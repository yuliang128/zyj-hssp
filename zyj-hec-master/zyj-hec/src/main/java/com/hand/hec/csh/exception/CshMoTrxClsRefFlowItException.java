package com.hand.hec.csh.exception;

import com.hand.hap.core.exception.BaseRuntimeExcepiton;

/**
 * description
 *
 * @author LJK 2019/04/03 16:29
 */
public class CshMoTrxClsRefFlowItException extends BaseRuntimeExcepiton {

	/**
	 * 只能有一个默认现金流量项
	 */
	public static final String GLD_SET_BOOK_DEFAULT_FLOW_ITEM_ONLY_ONE = "gld_set_book_default_flow_item_only_one";

	private static final String CODE_EXCEPTION = "CSH1051";
	/**
	 *
	 * //@param code 异常 code,通常与模块 CODE 对应
	 * @param descriptionKey 异常消息代码,系统描述中定义
	 * @param parameters
	 */
	public CshMoTrxClsRefFlowItException(String descriptionKey, Object[] parameters) {
		super(CODE_EXCEPTION, descriptionKey, parameters);
	}

}
