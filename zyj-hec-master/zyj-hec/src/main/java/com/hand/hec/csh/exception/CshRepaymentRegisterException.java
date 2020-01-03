package com.hand.hec.csh.exception;

import com.hand.hap.core.exception.BaseRuntimeExcepiton;

/**
 * <p>
 * 还款登记单相关的错误消息
 * </p>
 *
 * @author luhui 2019/03/11 10:21
 */
public class CshRepaymentRegisterException extends BaseRuntimeExcepiton {

    private static final long serialVersionUID = 1L;


    public static final String HD_NEGATIVE_AMOUNT_ERROR = "csh_repayment_register.hd_negative_amount_error";
    public static final String LN_NEGATIVE_AMOUNT_ERROR = "csh_repayment_register.ln_negative_amount_error";
    public static final String CHECK_STATUS_ERROR = "csh_repayment_register.status_error";
    public static final String TOTAL_AMOUNT_ERROR = "csh_repayment_register.total_amount_error";
    public static final String CHECK_DOC_NOT_EXISTS = "csh_repayment_register.doc_not_exists";
    public static final String CHECK_DOC_AMOUNT_ERROR = "csh_repayment_register.doc_amount_error";
    public static final String CHECK_DIST_AMOUNT_ERROR = "csh_repayment_register.dist_amount_error";
    public static final String CHECK_LINE_AMOUNT_EXCEED = "csh_repayment_register.line_amount_exceed";
    public static final String CHECK_DIST_AMOUNT_EXCEED = "csh_repayment_register.dist_amount_exceed";
    public static final String CHECK_NEGATIVE_AMOUNT = "csh_repayment_register.negative_amount";
    public static final String CHECK_NOT_NEED_ACCOUNTING = "csh_repayment_register.not_need_accounting";
    public static final String CHECK_CURRENCY_ERROR = "csh_repayment_register.currency_not_the_same";
    public static final String CHECK_LINE_NOT_EXISTS = "csh_repayment_register.line_not_exists";
    public static final String PERIOD_NOT_EXISTS = "csh_repayment_register.period_not_exists";

    public CshRepaymentRegisterException(String code, String message, Object[] parameters) {
        super(code, message, parameters);
    }

}
