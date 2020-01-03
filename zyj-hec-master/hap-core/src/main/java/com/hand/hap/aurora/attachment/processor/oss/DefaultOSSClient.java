package com.hand.hap.aurora.attachment.processor.oss;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.Bucket;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;
import org.apache.commons.fileupload.FileItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * description
 *
 * @author shira 2019/04/11 15:49
 */
public class DefaultOSSClient {

	private static Logger logger = LoggerFactory.getLogger(DefaultOSSClient.class);

	public final static String ENDPOINT;

	public final static String ACCESS_KEY_ID;

	public final static String ACCESS_KEY_SECRET;

	public final static String BACKET_NAME;

	public final static String FOLDER;

	//初始化属性
	static {
		ENDPOINT = OSSClientConfigProperty.ENDPOINT;
		ACCESS_KEY_ID = OSSClientConfigProperty.ACCESS_KEY_ID;
		ACCESS_KEY_SECRET = OSSClientConfigProperty.ACCESS_KEY_SECRET;
		BACKET_NAME = OSSClientConfigProperty.BACKET_NAME;
		FOLDER = OSSClientConfigProperty.FOLDER;
	}


	private static  OSSClient ossClient = new OSSClient(ENDPOINT, ACCESS_KEY_ID, ACCESS_KEY_SECRET, DefaultClientConfiguration.create());


	public static String createBucket(String bucketName){
		//存储空间
		final String bucketNames=bucketName;
		if(!ossClient.doesBucketExist(bucketName)){
			//创建存储空间
			Bucket bucket=ossClient.createBucket(bucketName);
			return bucket.getName();
		}
		return bucketNames;
	}


	public static  void deleteBucket( String bucketName){
		ossClient.deleteBucket(bucketName);
		logger.info("删除" + bucketName + "Bucket成功");
	}

	/**
	 * 创建模拟文件夹
	 * @param bucketName 存储空间
	 * @param folderName   模拟文件夹名如"qj_nanjing/"
	 * @return  文件夹名
	 */
	public  static String createFolder(String bucketName,String folderName){
		//文件夹名
		final String key = folderName;
		//判断文件夹是否存在，不存在则创建
		if(!ossClient.doesObjectExist(bucketName, key)){
			//创建文件夹
			ossClient.putObject(bucketName, key, new ByteArrayInputStream(new byte[0]));
			logger.info("创建文件夹成功");
			//得到文件夹名
			OSSObject object = ossClient.getObject(bucketName, key);
			String fileDir=object.getKey();
			return fileDir;
		}
		return key;
	}


	/**
	 *
	 * @param bucketName bucketName
	 * @param key  oss上文件全路径
	 * @param fileItem fileItem
	 * @return
	 */
	public static String putObject(String bucketName, String key, FileItem fileItem){
		String resultStr = null;
		try {
			//以输入流的形式上传文件
			InputStream is = fileItem.getInputStream();
			Long fileSize = fileItem.getSize();
			String fileName = key.substring(key.lastIndexOf('/') + 1);

			//创建上传Object的Metadata
			ObjectMetadata metadata = new ObjectMetadata();
			metadata.setContentLength(is.available());
			metadata.setCacheControl("no-cache");
			metadata.setHeader("Pragma", "no-cache");
			metadata.setContentEncoding("utf-8");
			metadata.setContentType(getContentType(fileName));
			metadata.setContentDisposition("filename/filesize=" + fileName + "/" + fileSize + "Byte.");

			PutObjectResult putResult = ossClient.putObject(bucketName, key, is, metadata);

			resultStr = putResult.getETag();
		} catch (Exception e) {
			logger.error("上传阿里云OSS服务器异常." + e.getMessage(), e);
		}
		return resultStr;
	}


	public static String putObject(String key, FileItem fileItem){
		return putObject(BACKET_NAME, key, fileItem);
	}

	public static InputStream getObject(String key){
		OSSObject oSSObject = ossClient.getObject(BACKET_NAME, key);
		return oSSObject.getObjectContent();
	}





	/**
	 * 根据key删除OSS服务器上的文件
	 * @param ossClient  oss连接
	 * @param bucketName  存储空间
	 * @param folder  模拟文件夹名 如"qj_nanjing/"
	 * @param key Bucket下的文件的路径名+文件名 如："upload/cake.jpg"
	 */
	public static void deleteFile(OSSClient ossClient, String bucketName, String folder, String key){
		ossClient.deleteObject(bucketName, folder + key);
		logger.info("删除" + bucketName + "下的文件" + folder + key + "成功");
	}


	/**
	 * 通过文件名判断并获取OSS服务文件上传时文件的contentType
	 * @param fileName 文件名
	 * @return 文件的contentType
	 */
	public static  String getContentType(String fileName){
		//文件的后缀名
		String fileExtension = fileName.substring(fileName.lastIndexOf("."));

		if(".bmp".equalsIgnoreCase(fileExtension)) {
			return "image/bmp";
		}
		if(".gif".equalsIgnoreCase(fileExtension)) {
			return "image/gif";
		}
		if(".jpeg".equalsIgnoreCase(fileExtension) || ".jpg".equalsIgnoreCase(fileExtension)  || ".png".equalsIgnoreCase(fileExtension) ) {
			return "image/jpeg";
		}
		if(".html".equalsIgnoreCase(fileExtension)) {
			return "text/html";
		}
		if(".txt".equalsIgnoreCase(fileExtension)) {
			return "text/plain";
		}
		if(".vsd".equalsIgnoreCase(fileExtension)) {
			return "application/vnd.visio";
		}
		if(".ppt".equalsIgnoreCase(fileExtension) || "pptx".equalsIgnoreCase(fileExtension)) {
			return "application/vnd.ms-powerpoint";
		}
		if(".doc".equalsIgnoreCase(fileExtension) || "docx".equalsIgnoreCase(fileExtension)) {
			return "application/msword";
		}
		if(".xml".equalsIgnoreCase(fileExtension)) {
			return "text/xml";
		}

		//默认返回类型
		return "image/jpeg";
	}


}
