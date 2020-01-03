package com.hand.hec.fnd.exception;


import com.hand.hap.core.exception.BaseException;

/**
 * <p>
 * 会计期定义异常
 * </p>
 *
 * @author $JK.Lu$ 2019/01/15 19:36
 */
public class GldPeriodSetException extends BaseException {

	/**
	 * 该会计期代码已经被帐套定义引用, 禁止删除!
	 */
	public static final String FND2120_PERIOD_SET_CODE_USING = "fnd2120_period_set_code_using";


	private static final String CODE_EXCEPTION = "FND2120";

	public GldPeriodSetException(String code, String descriptionKey, Object[] parameters) {
		super(code, descriptionKey, parameters);
	}

	public GldPeriodSetException(String descriptionKey, Object[] parameters) {
		super(CODE_EXCEPTION, descriptionKey, parameters);
	}
}
