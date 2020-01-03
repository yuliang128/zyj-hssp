package com.hand.hap.aurora.attachment.processor;

import com.hand.hap.aurora.attachment.processor.file.FileAttachmentProcessor;
import com.hand.hap.aurora.attachment.processor.oss.OSSAttachmentProcessor;

import static com.hand.hap.aurora.attachment.constant.StorageEnum.FILE;
import static com.hand.hap.aurora.attachment.constant.StorageEnum.OSS;

/**
 * description
 *
 * @author shira 2019/04/18 9:53
 */
public class AttachmentProcessorFactory {


	public static IAttachmentProcessor createInstance(String saveType){
		if(FILE.getStorage().equals(saveType)){
			return new FileAttachmentProcessor();
		}else if(OSS.getStorage().equals(saveType)){
			return new OSSAttachmentProcessor();
		}

		return new FileAttachmentProcessor();
	}




}
