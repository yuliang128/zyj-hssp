package com.hand.hap.aurora.attachment.processor;

import com.hand.hap.attachment.UpConstants;
import com.hand.hap.aurora.attachment.dto.FndAtmAttachment;
import com.hand.hap.aurora.attachment.exception.FndAtmAttachmentException;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * description
 *
 * @author shira 2019/04/23 10:13
 */
public abstract class AbstractAttachmentProcessor implements  IAttachmentProcessor{

	protected Logger logger = LoggerFactory.getLogger(getClass());

	protected String status = UpConstants.SUCCESS;

	protected AttachmentConfigurationProperty attachmentConfiguration = new AttachmentConfigurationProperty();
	protected FndAtmAttachment attachmentCommonInfo = null;

	protected List<FileItem> fileItemList  = new ArrayList<>(); ;

	protected List<FndAtmAttachment> attachmentInfoList  = new ArrayList<>();;


	protected void initUpload(HttpServletRequest request) {

		if (!ServletFileUpload.isMultipartContent(request)) {
			status = UpConstants.NOT_FILE_ERROR;
			return;
		}

		attachmentCommonInfo = new  FndAtmAttachment();
		//attachmentCommonInfo.setSaveType(StorageEnum.FILE.getStorage());
		attachmentCommonInfo.setTableName(request.getParameter(FndAtmAttachment.FIELD_TABLE_NAME));
		attachmentCommonInfo.setTablePkValue( Long.parseLong(request.getParameter(FndAtmAttachment.FIELD_TABLE_PK_VALUE)) );

		ServletFileUpload servletFileUpload = attachmentConfiguration.assembleServletFileUpload();

		try {
			fileItemList = servletFileUpload.parseRequest(request);
		} catch (FileUploadException e) {
			logger.error("文件上传错误！", e);

			status = UpConstants.UPLOAD_ERROR;
		}
	}


	@Override
	public List<FndAtmAttachment> upload(HttpServletRequest request) {
		initUpload(request);

		for (FileItem fileItem : fileItemList) {
			FndAtmAttachment attachmentInfo = new FndAtmAttachment();
			try {
				if (fileItem.isFormField()) {
					// upload common value
					String name = fileItem.getFieldName();
					String value = fileItem.getString(attachmentConfiguration.getEncoding());
					logger.debug("{} = {}", name, value);
					continue;
				} else {

					// upload file
					String canonicalFilename = fileItem.getName();
					if (canonicalFilename == null || "".equals(canonicalFilename.trim())) {
						continue;
					}

					validate(fileItem);

					//注意：不同的浏览器提交的文件名是不一样的，有些浏览器提交上来的文件名是带有路径的，如：  c:\a\b\1.txt，而有些只是单纯的文件名，如：1.txt
					canonicalFilename = canonicalFilename.substring(
							canonicalFilename.lastIndexOf(attachmentConfiguration.getDirSeparator()) + 1);
					String simpleFileName = canonicalFilename.substring(0, canonicalFilename.lastIndexOf('.'));
					String fileExt = canonicalFilename.substring(canonicalFilename.lastIndexOf('.') + 1);

					// 得到目标文件
					String saveFilename = creatUniqueFileName(simpleFileName, fileExt );
					String fileSavePath = attachmentCommonInfo.getFilePath() +  saveFilename;

					attachmentCommonInfo.setFileName(canonicalFilename);
					attachmentCommonInfo.setFilePath(fileSavePath);
					attachmentCommonInfo.setFileSize( new BigDecimal(fileItem.getSize()) );
					attachmentCommonInfo.setFileType(fileExt);
					attachmentCommonInfo.setMineType(fileItem.getContentType());

					doUpload(fileItem);

					BeanUtils.copyProperties(attachmentCommonInfo, attachmentInfo);
					logger.debug("文件已成功上传至路径："+fileSavePath);
				}
			} catch (Exception e) {
				logger.error("文件上传错误！", e);
				this.status = UpConstants.UPLOAD_ERROR;
			}

			attachmentInfoList.add(attachmentInfo);
		}




		return attachmentInfoList;
	}


	protected abstract void doUpload( FileItem fileItem );


	@Override
	public String getStatus() {
		return status;
	}



	/**
	 * 生成上传文件的文件名，文件名以：uuid+"_"+文件的原始名称
	 *
	 * @param simpleFileName 文件名(不包含扩展名)
	 * @param fileExt 文件扩展名
	 * @return simpleFileName + "_" +uuid+ "." + ext
	 */
	private String creatUniqueFileName(String simpleFileName, String fileExt) {  //2.jpg
		return simpleFileName + "_" + UUID.randomUUID().toString() + "." + fileExt;
	}


	/**
	 * 验证文件是否符合上传规范.
	 *
	 * @param fileItem 文件信息
	 *
	 */
	private void validate(FileItem fileItem) {
		String fileName = fileItem.getName();

		// 验证文件大小, 新建的无内容文件大小为0KB
		if (fileItem.getSize() < 0 || StringUtils.isEmpty(fileName)) {
			throw new FndAtmAttachmentException(UpConstants.ERROR_UPLOAD_FILE_EMPTY_ERROR, new Object[0]);
		}

	}

}
