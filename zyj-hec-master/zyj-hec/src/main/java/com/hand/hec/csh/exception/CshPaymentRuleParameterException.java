package com.hand.hec.csh.exception;

import com.hand.hap.core.exception.BaseException;

/**
 * 支付权限规则代码参数定义-sql异常
 *
 * @author luhui
 * @date 2019/02/27
 */
public class CshPaymentRuleParameterException extends BaseException {

    private static final long serialVersionUID = 1L;

    /**
     * 支付权限规则代码定义格式不正确!
     */
    public static final String PAYMENT_RULE_PARAMETER_SQL = "error.payment_rule_parameter_sql";

    public CshPaymentRuleParameterException(String code, String message, Object[] parameters) {
        super(code, message, parameters);
    }

}
