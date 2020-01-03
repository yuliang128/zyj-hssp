package com.hand.hec.fnd.exception;


import com.hand.hap.core.exception.BaseException;

/**
 * <p>
 * 创建会计期间异常
 * </p>
 *
 * @author $JK.Lu$ 2019/01/22 18:36
 */
public class GldAePeriodStatusException extends BaseException {

	/**
	 * 欲打开的期间之前一个期间没有被打开过，不能打开当前期间！请先打开前一个期间
	 */
	public static final String GLD_LAST_PERIOD_NOT_OPENED_ERROR = "gld_last_period_not_opened_error";

	/**
	 * 请先打开当前期间的后一个期间，否则当前期间无法重新打开。
	 */
	public static final String GLD_NEXT_PERIOD_NOT_OPENED_ERROR = "gld_next_period_not_opened_error";

	/**
	 * 请先关闭前一个期间，否则不能关闭当前期间！
	 */
	public static final String GLD_LAST_PERIOD_NOT_CLOSED_ERROR = "gld_last_period_not_closed_error";

	private static final String CODE_EXCEPTION = "FND5800";

	public GldAePeriodStatusException(String code, String descriptionKey, Object[] parameters) {
		super(code, descriptionKey, parameters);
	}

	public GldAePeriodStatusException(String descriptionKey, Object[] parameters) {
		super(CODE_EXCEPTION, descriptionKey, parameters);
	}
}
