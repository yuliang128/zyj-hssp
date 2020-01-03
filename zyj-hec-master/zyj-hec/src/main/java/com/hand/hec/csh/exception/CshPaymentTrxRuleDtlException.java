package com.hand.hec.csh.exception;

import com.hand.hap.core.exception.BaseException;

/**
 * 付款事务生成规则定义-分组规则 分组规则重复异常
 *
 * @author luhui
 * @date 2019/03/04
 */
public class CshPaymentTrxRuleDtlException extends BaseException {

    private static final long serialVersionUID = 1L;

    /**
     * 支付权限规则代码定义格式不正确!
     */
    public static final String PAYMENT_TRX_RULE_UNIQUE = "csh_payment_trx_rule_dtl.duplicate";

    public CshPaymentTrxRuleDtlException(String code, String message, Object[] parameters) {
        super(code, message, parameters);
    }

}
