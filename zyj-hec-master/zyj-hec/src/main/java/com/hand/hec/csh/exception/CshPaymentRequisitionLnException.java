package com.hand.hec.csh.exception;

import com.hand.hap.core.exception.BaseException;

/**
 * 参数定义异常.
 *
 * @author njq.niu@hand-china.com
 * @date 2016/4/25
 */
public class CshPaymentRequisitionLnException extends BaseException {

    private static final long serialVersionUID = 1L;

    /**
     * 银行账号不能为空，请先维护好相关银行账户信息.
     */
    public static final String ACCOUNT_NUMBER_NULL = "error.account_number_null";

    /**
     * 借款超出申请余额.
     */
    public static final String LOAD_AMOUNT_ERROR = "error.loan_amount_error";

    /**
     * 申请金额不能大于 申请单需付金额 减 已申请金额.
     */
    public static final String REQ_AMOUNT_ERROR = "error.req_amount_error";

    public CshPaymentRequisitionLnException(String code, String message, Object[] parameters) {
        super(code, message, parameters);
    }

}
