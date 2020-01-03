package com.hand.hap.aurora.attachment.processor.oss;


import com.hand.hap.attachment.UpConstants;
import com.hand.hap.aurora.attachment.constant.StorageEnum;
import com.hand.hap.aurora.attachment.dto.FndAtmAttachment;
import com.hand.hap.aurora.attachment.processor.AbstractAttachmentProcessor;
import org.apache.commons.fileupload.FileItem;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLEncoder;

/**
 * description
 *
 * @author shira 2019/04/11 15:35
 */
public class OSSAttachmentProcessor extends AbstractAttachmentProcessor {

	@Override
	protected void initUpload(HttpServletRequest request) {
		super.initUpload(request);
		attachmentCommonInfo.setSaveType(StorageEnum.OSS.getStorage());

		String folder = request.getParameter(FndAtmAttachment.FIELD_FILE_PATH);

		if (null == folder || "".equals(folder)) {
			folder = OSSClientConfigProperty.FOLDER;
		}

		attachmentCommonInfo.setFilePath(folder);

	}

	@Override
	protected void doUpload(FileItem fileItem) {
		DefaultOSSClient.putObject(attachmentCommonInfo.getFilePath(), fileItem);
	}

	@Override
	public void download(FndAtmAttachment attachment, HttpServletResponse response) {

		try (InputStream InputStream = DefaultOSSClient.getObject(attachment.getFilePath());
				ServletOutputStream outputStream = response.getOutputStream()){

			response.addHeader("Content-Disposition", "attachment;filename=\"" + URLEncoder
					.encode(attachment.getFileName(), attachmentConfiguration.getEncoding()) + "\"");
			response.setContentType(attachment.getFileType() + ";charset=" + attachmentConfiguration.getEncoding());
			response.setHeader("Accept-Ranges", "bytes");

			response.setContentLength(attachment.getFileSize().intValue());

			byte[] buf = new byte[attachmentConfiguration.getBufferSize()];
			int readLength;
			while (((readLength = InputStream.read(buf)) != -1)) {
				outputStream.write(buf, 0, readLength);
			}
			outputStream.flush();


		} catch (Exception e) {
			logger.error("文件下载失败！", e);
			this.status = UpConstants.ERROR_DOWNLOAD_FILE_ERROR;
		}

	}


	@Override
	public void remove(FndAtmAttachment attachment) {

	}
}
