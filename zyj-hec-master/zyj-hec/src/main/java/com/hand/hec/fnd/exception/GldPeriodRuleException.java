package com.hand.hec.fnd.exception;


import com.hand.hap.core.exception.BaseException;

/**
 * <p>
 * 会计期间规则异常
 * </p>
 *
 * @author $JK.Lu$ 2019/01/15 19:36
 */
public class GldPeriodRuleException extends BaseException {

	/**
	 * 输入的值重复，请重新输入!
	 */
	public static final String SYS_DUP_VAL_ON_INDEX = "sys_dup_val_on_index";

	/**
	 * 调整期间定义不合理,请输入合适的调整期间定义数据!
	 */
	public static final String GLD_CREATE_ADJUSTMENT_PERIOD_ERROR = "gld_create_adjustment_period_error";

	/**
	 * 期间定义不合理，请输入合适的期间定义数据！
	 */
	public static final String GLD_CREATE_PERIOD_ERROR = "gld_create_period_error";

	/**
	 * 期间规则定义不完整，请输入合适的期间规则定义数据！
	 */
	public static final String GLD_PERIOD_RULE_CHECK_ERROR = "gld_period_rule_check_error";

	/**
	 * 系统发现期间日期首尾不相连！
	 */
	public static final String GLD_PERIOD_DATE_ERROR = "gld_period_date_error";

	private static final String CODE_EXCEPTION = "FND2120";

	public GldPeriodRuleException(String code, String descriptionKey, Object[] parameters) {
		super(code, descriptionKey, parameters);
	}

	public GldPeriodRuleException(String descriptionKey, Object[] parameters) {
		super(CODE_EXCEPTION, descriptionKey, parameters);
	}
}
