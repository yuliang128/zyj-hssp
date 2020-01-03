package com.hand.hec.csh.exception;

import com.hand.hap.core.exception.BaseRuntimeExcepiton;

/**
 * description
 *
 * @author LJK 2019/04/03 16:29
 */
public class CshDocPayAccEntityException extends BaseRuntimeExcepiton {

	/**
	 * 只能有一个默认现金流量项
	 */
	public static final String DOC_STATUS_ERROR = "doc_return_status_check";

	private static final String CODE_EXCEPTION = "CSH5040";
	/**
	 *
	 * //@param code 异常 code,通常与模块 CODE 对应
	 * @param descriptionKey 异常消息代码,系统描述中定义
	 * @param parameters
	 */
	public CshDocPayAccEntityException(String descriptionKey, Object[] parameters) {
		super(CODE_EXCEPTION, descriptionKey, parameters);
	}

}
