package com.hand.hap.aurora.attachment.controllers;


import com.hand.hap.attachment.UpConstants;
import com.hand.hap.aurora.attachment.dto.FndAtmAttachment;
import com.hand.hap.aurora.attachment.exception.FndAtmAttachmentException;
import com.hand.hap.aurora.attachment.service.IFndAtmAttachmentService;
import com.hand.hap.core.IRequest;
import com.hand.hap.core.exception.TokenException;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.util.List;
import java.util.Locale;
import java.util.UUID;


/**
 * xx控制器
 *
 * @author xxx YYYY-mm-dd
 */

@Controller
public class FndAtmAttachmentController extends BaseController {

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private IFndAtmAttachmentService service;


    /**
     * 文件下载默认编码.
     */
    private static final String ENC = "UTF-8";

    private int bufferSize = 500 * 1024;

    @RequestMapping(value = "/fnd/atm-attachment/query", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData query(HttpServletRequest request, FndAtmAttachment dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page, @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize) {
        IRequest requestContext = createRequestContext(request);

        List<FndAtmAttachment> fndAtmAttachmentList = service.queryFndAtmAttachment(requestContext, dto, page, pageSize);
        return new ResponseData(fndAtmAttachmentList);
    }

    @RequestMapping(value = "/fnd/atm-attachment/remove")
    @ResponseBody
    public ResponseData remove(HttpServletRequest request, @RequestBody List<FndAtmAttachment> fndAtmAttachmentList) {
        IRequest requestContext = createRequestContext(request);
        service.remove(requestContext, fndAtmAttachmentList);
        return new ResponseData(fndAtmAttachmentList);
    }


    @RequestMapping(value = "/fnd/atm-attachment/upload")
    @ResponseBody
    public Long upload(HttpServletRequest request, Locale locale) {
        List<FndAtmAttachment> fndAtmAttachmentList = service.upload(request);
        return fndAtmAttachmentList.get(0).getAttachmentId();
    }


    /**
     * 具体查看某个附件.
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @throws TokenException 没有_token,或者不匹配
     */
    @RequestMapping(value = "/fnd/atm-attachment/download")
    public void download(HttpServletRequest request, HttpServletResponse response) throws Exception {
        IRequest requestContext = createRequestContext(request);
        String attachmentId = request.getParameter("attachmentId");
        if (StringUtils.isEmpty(attachmentId)) {
            throw new FndAtmAttachmentException(UpConstants.ERROR_DOWNLOAD_FILE_ERROR, new Object[0]);
        }

        String status = service.download(Long.valueOf(attachmentId), response);
        if (!UpConstants.SUCCESS.equals(status)) {
            throw new FndAtmAttachmentException(status, new Object[0]);
        }

    }


    @RequestMapping("/app/APP2030/app_uploadFile.screen")
    public ModelAndView appUploadFile(HttpServletRequest request, String tableName, String tablePkValue, @RequestParam(value = "showDelete", required = false) Boolean showDelete) {
        ModelAndView view = new ModelAndView("app/APP2030/app_uploadFile");
        return assembleModel(  view ,  tableName,  tablePkValue,   showDelete);
    }

    @RequestMapping("/app/APP2030/app_uploadFile_cannotUpload.screen")
    public ModelAndView appCannotUploadFile(HttpServletRequest request, String tableName, String tablePkValue, @RequestParam(value = "showDelete", required = false) Boolean showDelete) {
        ModelAndView view = new ModelAndView("app/APP2030/app_uploadFile_cannotUpload");
        return assembleModel(  view ,  tableName,  tablePkValue,   showDelete);
    }

    private ModelAndView assembleModel( ModelAndView view , String tableName, String tablePkValue,  Boolean showDelete){

        String uuid = UUID.randomUUID().toString().replace("-", "").toUpperCase();

        view.addObject("uuid", uuid);
        view.addObject("tableName", tableName);
        view.addObject("tablePkValue", tablePkValue);

        if(showDelete == null ){
            showDelete = false;
        }

        view.addObject("showDelete", showDelete);

        return view;
    }

    @RequestMapping(value = "/preview.screen")
    public ModelAndView previewModelAndView(FndAtmAttachment dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page, @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        ModelAndView view = new ModelAndView("preview");
        IRequest requestContext = createRequestContext(request);
        List<FndAtmAttachment> fndAtmAttachmentList = service.queryFndAtmAttachment(requestContext, dto, page, pageSize);
        if (fndAtmAttachmentList != null && !fndAtmAttachmentList.isEmpty()) {
            for (FndAtmAttachment atmAttachment : fndAtmAttachmentList) {
                atmAttachment.setDivId("detail_" + atmAttachment.getAttachmentId());
            }
        }
        view.addObject("fnd_attachmentPreview_list", fndAtmAttachmentList);
        return view;
    }


    @RequestMapping(value = "/fnd/atm-attachment/preview")
    @ResponseBody
    public void preview(FndAtmAttachment dto, HttpServletRequest request, HttpServletResponse resp) {
        String userAgent = request.getHeader("User-Agent");
        if (userAgent.indexOf("safari") != -1) {
            resp.setContentType("text/html;charset=ISO8859-1");
            resp.setCharacterEncoding("ISO8859-1");
        } else {
            resp.setContentType("text/html;charset=UTF-8");
            resp.setCharacterEncoding("UTF-8");
        }

        String attachmentId = String.valueOf(dto.getAttachmentId());
        try {
            responseFileContents(attachmentId, request, resp);
        } catch (Exception e) {
            System.out.print("获取文件时报错，错误消息为：");
            System.out.println(e.getLocalizedMessage());
        }
    }


    private void responseFileContents(String attachmentId, HttpServletRequest req, HttpServletResponse resp) throws Exception {
        IRequest requestContext = createRequestContext(req);
        // Input & Output streams
        InputStream is = null;
        OutputStream os = null;
        ReadableByteChannel rbc = null;
        WritableByteChannel wbc = null;

        try {
            // Retrieve attachment info: file name, file_path etc.
            FndAtmAttachment attachmentInfo = new FndAtmAttachment();
            attachmentInfo.setAttachmentId(Long.valueOf(attachmentId));
            attachmentInfo = service.selectByPrimaryKey(requestContext, attachmentInfo);
            resp.setHeader("cache-control", "must-revalidate");
            resp.setHeader("pragma", "public");
            resp.setHeader("Content-Type", attachmentInfo.getMineType());
            System.out.println("格式" + attachmentInfo.getMineType());
            resp.setHeader("Content-disposition", "inline;" + processFileName(req, attachmentInfo.getFileName()));
            int fileSize = attachmentInfo.getFileSize().intValue();
            resp.setHeader("Content-Length", fileSize + "");
            try {
                Class.forName("org.apache.catalina.startup.Bootstrap");
                if (fileSize > 0) {
                    resp.setContentLength(fileSize);
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            String path = attachmentInfo.getFilePath();
            if (path != null) {
                // File system storage
                File file = new File(path);
                if (file.exists()) {
                    os = resp.getOutputStream();
                    is = new FileInputStream(path);
                    rbc = Channels.newChannel(is);
                    wbc = Channels.newChannel(os);
                    ByteBuffer buf = ByteBuffer.allocate(this.bufferSize);
                    while ((rbc.read(buf)) > 0) {
                        buf.flip();
                        wbc.write(buf);
                        buf.compact();
                        os.flush();
                    }
                } else {
                    throw new ServletException("Attachment is not exists in file system");
                }
            } else {
                //                // Database storage
                //                Blob content = attachmentInfo.getContent();
                //                if (content != null) {
                //                    os = resp.getOutputStream();
                //                    is = content.getBinaryStream();
                //                    rbc = Channels.newChannel(is);
                //                    wbc = Channels.newChannel(os);
                //                    ByteBuffer buf = ByteBuffer.allocate(this.bufferSize);
                //                    while ((rbc.read(buf)) > 0) {
                //                        buf.flip();
                //                        wbc.write(buf);
                //                        buf.compact();
                //                        os.flush();
                //                    }
                //                }
            }
            resp.setHeader("Connection", "close");
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
                if (os != null) {
                    os.close();
                }
            } catch (Exception ex) {
                // NOOP
            }
        }
    }

    private String processFileName(HttpServletRequest request, String filename) throws UnsupportedEncodingException {
        String userAgent = request.getHeader("User-Agent");
        String rtn = null;
        if (userAgent.indexOf("safari") != -1) {
            rtn = "filename=\"" + new String(filename.getBytes("UTF-8"), "ISO8859-1") + "\"";
            return rtn;
        }
        String newFileName = URLEncoder.encode(filename, "UTF8");
        rtn = "filename=\"" + newFileName + "\"";
        return rtn;
    }

}