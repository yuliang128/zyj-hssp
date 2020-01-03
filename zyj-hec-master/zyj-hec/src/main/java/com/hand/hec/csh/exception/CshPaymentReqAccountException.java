package com.hand.hec.csh.exception;

import com.hand.hap.core.exception.BaseException;

/**
 * 参数定义异常.
 *
 * @author njq.niu@hand-china.com
 * @date 2016/4/25
 */
public class CshPaymentReqAccountException extends BaseException {

    private static final long serialVersionUID = 1L;

    /**
     * 日期格式转换失败
     */
    public static final String DATE_FORMAT_CONVERSION_FAIL = "error.date_format_conversion_fail";

    /**
     * 请先创建凭证
     */
    public static final String CREATE_ACCOUNT_FIRST = "error.create_account_first";

    /**
     * 借款申请单无需复核,请联系系统管理员
     */
    public static final String PAYMENT_REQ_AUDIT_RECHECK_NOT_NEED = "error.payment_req_audit_recheck_not_need";

    /**
     * 当前借款单不处于待复核状态,请确认单据状态
     */
    public static final String PAYMENT_REQ_CONFIRM_STATUS = "error.payment_req_confirm_status";

    /**
     * 凭证借贷不平衡，不能审核
     */
    public static final String LOAN_VOUCHER_NOT_BALANCE = "error.loan_voucher_not_balance";

    /**
     * 借款单连同创建的费用申请已报销，不能拒绝借款单
     */
    public static final String PAYMENT_REQ_ASSO_EXP_REQ_EXPENSE = "error.payment_req_asso_exp_req_expense";

    /**
     *费用申请单存在关闭的行，不允许拒绝
     */
    public static final String EXP_REQUISITION_EXISTS_CLOSED_LINE = "error.exp_requisition_exists_closed_line";

    /**
     *借方和贷方金额不能都为空
     */
    public static final String DEBIT_CREDIT_CANNOT_BE_NULL = "error.debit_credit_cannot_be_null";

    /**
     *借方和贷方金额不能同时输入
     */
    public static final String DEBIT_CREDIT_CANNOT_ALL_INPUT = "error.debit_credit_cannot_all_input";


    public CshPaymentReqAccountException(String code, String message, Object[] parameters) {
        super(code, message, parameters);
    }

}
