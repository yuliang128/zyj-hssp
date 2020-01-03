package com.hand.hap.aurora.attachment.processor.file;

import com.hand.hap.attachment.UpConstants;
import com.hand.hap.aurora.attachment.constant.StorageEnum;
import com.hand.hap.aurora.attachment.dto.FndAtmAttachment;
import com.hand.hap.aurora.attachment.processor.AbstractAttachmentProcessor;
import org.apache.commons.fileupload.FileItem;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

/**
 * description
 *
 * @author shira 2019/04/17 15:19
 */
public class FileAttachmentProcessor extends AbstractAttachmentProcessor {

	@Override
	protected void initUpload(HttpServletRequest request) {
		super.initUpload(request);
		attachmentCommonInfo.setSaveType(StorageEnum.FILE.getStorage());
		attachmentCommonInfo.setFilePath(getSavePath(request));

	}

	@Override
	protected void doUpload(FileItem fileItem) {
		try {
			File targetFile = new File(attachmentCommonInfo.getFilePath());
			fileItem.write(targetFile);
		} catch (Exception e) {
			logger.error("文件上传错误！", e);
			this.status = UpConstants.UPLOAD_ERROR;
		}
	}

	@Override
	public void download(FndAtmAttachment attachment, HttpServletResponse response) {
		File file = new File(attachment.getFilePath());
		if (file.exists()) {
			try {
				response.addHeader("Content-Disposition", "attachment;filename=\"" + URLEncoder
						.encode(attachment.getFileName(), attachmentConfiguration.getEncoding()) + "\"");
				response.setContentType(attachment.getFileType() + ";charset=" + attachmentConfiguration.getEncoding());
				response.setHeader("Accept-Ranges", "bytes");
				int fileLength = (int) file.length();
				response.setContentLength(fileLength);

				if (fileLength > 0) {
					writeFileToResp(response, file);
				}

			} catch (Exception e) {
				logger.error("文件下载失败！", e);
				this.status = UpConstants.ERROR_DOWNLOAD_FILE_ERROR;
			}

		} else {
			logger.error("要下载的文件不存在！");
			this.status = UpConstants.ERROR_DOWNLOAD_FILE_ERROR;
		}

	}





	/**
	 * 将文件对象的流写入Responsne对象.
	 *
	 * @param response HttpServletResponse
	 * @param file     File
	 * @throws FileNotFoundException 找不到文件异常
	 * @throws IOException           IO异常
	 */
	private void writeFileToResp(HttpServletResponse response, File file) throws Exception {
		byte[] buf = new byte[attachmentConfiguration.getBufferSize()];
		try (InputStream inStream = new FileInputStream(file);
				ServletOutputStream outputStream = response.getOutputStream()) {
			int readLength;
			while (((readLength = inStream.read(buf)) != -1)) {
				outputStream.write(buf, 0, readLength);
			}
			outputStream.flush();

		}
	}

	@Override
	public void remove(FndAtmAttachment attachment) {
		File target = new File(attachment.getFilePath());
		if(target.exists()&& target.isFile()){
			boolean delete = target.delete();
		}
	}

	private String getSavePath(HttpServletRequest request){
		String saveDir = request.getParameter(FndAtmAttachment.FIELD_FILE_PATH);

		if (null == saveDir || "".equals(saveDir)) {
			// save file to the parent dir of project root dir
			saveDir = attachmentConfiguration.getSavePath();

		} else {
			saveDir = request.getSession().getServletContext().getRealPath(saveDir);
			saveDir = saveDir.replace("file:/","");
		}

		File saveDirectory = new File(saveDir);
		if (!saveDirectory.exists()) {
			boolean result = saveDirectory.mkdir();

		}

		return saveDir;
	}
}

