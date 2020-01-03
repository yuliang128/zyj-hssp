package com.hand.hap.aurora.attachment.exception;

import com.hand.hap.core.exception.BaseRuntimeExcepiton;

/**
 * description
 *
 * @author shira 2019/04/17 16:07
 */
public class FndAtmAttachmentException extends BaseRuntimeExcepiton {

	private static final String CODE_EXCEPTION = "fnd_atm_attachment";
	/**
	 * 下载文件不存在.
	 */
	public static final String ERROR_INVALID_PARAM = "msg.warning.fndAtmAttachment.invalid.param";

	public FndAtmAttachmentException(String descriptionKey, Object[] parameters) {
		super(CODE_EXCEPTION, descriptionKey, parameters);
	}
}
