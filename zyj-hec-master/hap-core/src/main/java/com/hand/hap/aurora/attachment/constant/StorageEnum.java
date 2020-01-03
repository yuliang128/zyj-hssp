package com.hand.hap.aurora.attachment.constant;

/**
 * description
 *
 * @author shira 2019/04/18 10:20
 */

public enum StorageEnum {
	FILE("file"),
	OSS("oss");


	private String storage;

	StorageEnum(String storage){
		this.storage = storage;
	}

	public String getStorage(){
		return this.storage;
	}

}
