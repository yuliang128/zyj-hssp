package com.hand.hap.aurora.attachment.processor;

import com.hand.hap.aurora.attachment.dto.FndAtmAttachment;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * description
 *
 * @author shira 2019/04/17 15:17
 */
public interface IAttachmentProcessor {


	/**
	 * upload Attachment
	 */
	List<FndAtmAttachment> upload(HttpServletRequest request);

	/**
	 * download Attachment
	 */
	void download(FndAtmAttachment attachment, HttpServletResponse response);

	void remove(FndAtmAttachment attachment);

	String getStatus();



}
