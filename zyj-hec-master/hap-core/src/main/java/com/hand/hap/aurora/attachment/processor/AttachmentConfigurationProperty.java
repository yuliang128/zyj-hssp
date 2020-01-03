package com.hand.hap.aurora.attachment.processor;

import lombok.Data;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Objects;

/**
 * description
 *
 * @author shira 2019/04/23 10:25
 */
@Data
public class AttachmentConfigurationProperty {

	private Logger logger = LoggerFactory.getLogger(getClass());

	private static final Integer BUFFER_SIZE = 1024*100;

	/**
	 * 上传单个文件的大小的最大值
	 * 默认是设置为20*1024*1024字节，也就是20MB
	 */
	private static final long  SINGLE_FILE_SIZE_MAX = 30*1024*1024;

	/**
	 * 上传文件总量的最大值,
	 * 最大值=同时上传的多个文件的大小的最大值的和，目前设置为10MB
	 */
	private static final long  TOTAL_FILE_SIZE_MAX = 50*1024*1024;

	/**
	 * 字符编码格式
	 */
	protected static final String  ENCODING_UTF8 = "UTF-8";


	/**
	 * 文件夹分隔符
	 */
	private static final char DIR_SEPARATOR_UNIX = '/';
	private static final char DIR_SEPARATOR_WINDOWS = '\\';

	/**
	 * 默认文件保存路径
	 */
	public static final String  SAVE_PATH = "/attachment/";

	private String  savePath;

	private char  dirSeparator;

	private String encoding;

	/** 文件保存在项目内部，还是项目外部 **/
	private Boolean outOfProject = true;

	private Integer bufferSize;

	public  AttachmentConfigurationProperty(){
		dirSeparator = File.separatorChar;
		encoding = ENCODING_UTF8;
		bufferSize = BUFFER_SIZE;

		initSavePath();
	}


	public ServletFileUpload assembleServletFileUpload(){
		ServletFileUpload servletFileUpload = new ServletFileUpload(new DiskFileItemFactory());
		servletFileUpload.setProgressListener( (pBytesRead, pContentLength, pItems) -> logger.debug("文件大小为：" + pContentLength + ",当前已处理：" + pBytesRead));
		servletFileUpload.setHeaderEncoding(ENCODING_UTF8);

		servletFileUpload.setFileSizeMax(SINGLE_FILE_SIZE_MAX);
		servletFileUpload.setSizeMax(TOTAL_FILE_SIZE_MAX);

		return servletFileUpload;
	}


	/**
	 * 默认情况下：若设置了文件保存在项目内，则保存在 WEB-INF/attachment/ 下
	 * 			  若设置了文件保存在项目外，则保存在项目的同级目录 attachment/ 下
	 * 	若前台传了文件保存路径，则保存至该路径下，见：FileAttachmentProcessor#getSavePath(HttpServletRequest)
	 *
	 */
	private void initSavePath(){
		if(outOfProject){
			// save file to the parent dir of project root dir
			String projectRootDir =   Objects.requireNonNull(this.getClass().getClassLoader().getResource("")).toString();
			// "hap/WEB-INF/classes/".length  = 20
			savePath = projectRootDir.substring(0, projectRootDir.lastIndexOf(DIR_SEPARATOR_UNIX)-20) + SAVE_PATH;

		}else{
			// save file to WEB-INF/attachment
			String classRootDir = Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource("")).toString();
			// "/classes".length  = 8
			savePath = classRootDir.substring(0, classRootDir.lastIndexOf(DIR_SEPARATOR_UNIX)-8) + SAVE_PATH;
		}


		logger.debug("attachment savePath is [{}]",savePath);

		// 2.remove URI protocol
		String os = System.getProperty("os.name");
		if(os.toLowerCase().startsWith("win")){
			savePath = savePath.replace("file:/","");
		}else{
			savePath = savePath.replace("file:","");
		}

		logger.info("attachment savePath is [{}]",savePath);
	}


}
