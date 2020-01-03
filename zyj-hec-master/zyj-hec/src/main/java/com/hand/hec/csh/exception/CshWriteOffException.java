package com.hand.hec.csh.exception;

import com.hand.hap.core.exception.BaseRuntimeExcepiton;

/**
 * <p>
 * 核销相关的错误消息
 * </p>
 *
 * @author Tagin 2019/02/20 18:21
 */
public class CshWriteOffException extends BaseRuntimeExcepiton {

    private static final long serialVersionUID = 1L;

    /**
     * 本次核销金额超过了未核销金额！
     */
    public static final String WRITE_AMOUNT_OVER_ERROR = "error.write_amount_over_error";

    /**
     * 本次支付金额不能小于0，请重新输入！
     */
    public static final String NEGATIVE_AMOUNT_ERROR = "error.negative_amount_error";

    /**
     * 单据已被完全支付，请重新确认！
     */
    public static final String WRITE_OFF_COMPLETE_ERROR = "error.write_off_complete_error";

    /**
     * 本次支付金额大于未付金额，请重新确认！
     */
    public static final String PAYMENT_AMOUNT_GREATER_ERROR = "error.payment_amount_greater_error";

    /**
     * 核销日期所在期间，未打开！
     */
    public static final String WRITE_PERIOD_NOT_OPEN = "error.write_period_not_open";

    /**
     * 未能取得当前核算主体缺省的成本中心, 请检查参数指定！
     */
    public static final String DEFAULT_RESP_NOT_FOUND = "error.default_resp_not_found";

    /**
     * 未能取得对方核算主体缺省的成本中心，请检查参数指定！
     */
    public static final String DEFAULT_TO_RESP_NOT_FOUND = "error.default_to_resp_not_found";

    /**
     * 未能取到报销单核销科目，请检查用途代码定义！
     */
    public static final String REPORT_ACCOUNT_NOT_FOUND = "error.report_account_not_found";

    /**
     * 未能获取借款申请单的科目，请检查借款申请单凭证！
     */
    public static final String PAYMENT_REQUISITION_ACCOUNT_NOT_FOUND = "error.payment_requisition_account_not_found";

    /**
     * 未能取得汇率损益科目，请检查用途代码定义！
     */
    public static final String REVALUATION_ACCOUNT_NOT_FOUND = "error.revaluation_account_not_found";

    /**
     * 日期格式转换错误
     */
    public static final String DATE_FORMAT_ERROR = "error.date_format_error";

    /**
     * 核销金额合计不等于付款金额，不能过帐！
     */
    public static final String SUM_WRITE_OFF_AMOUNT_ERROR = "error.sum_write_off_amount_error";

    /**
     * 核销行退款金额合计必须等于退款总金额
     */
    public static final String SUM_WRITE_OFF_AMOUNT_NOT_EQUAL_RETURN_AMOUNT =
                    "error.sum_write_off_amount_not_equal_return_amount";

    /**
     * 退款金额超过可退金额，不能过帐！
     */
    public static final String RETURN_AMOUNT_ERROR = "error.return_amount_error";

    /**
     * 未能正确取得银行账户的成本中心和科目，请检查银行账户定义！
     */
    public static final String BANK_ACCOUNT_ERROR = "error.write_off_bank_account_error";

    /**
     * 未能取得当前核算主体缺省的成本中心, 请检查参数指定!
     */
    public static final String DEFAULT_RESP_CENTER = "error.default_resp_center_not_exists";

    /**
     * 未能取到报销单核销科目，请检查用途代码定义！
     */
    public static final String EXPENSE_CLEARING_NOT_EXISTS = "error.expense_clearing_not_exists";

    /**
     * 未能取得汇率损益科目，请检查用途代码定义！
     */
    public static final String REVALUATION_NOT_EXISTS = "error.revaluation_not_exists";

    /**
     * 未能取到现金内部往来应收科目，请检查用途代码定义！
     */
    public static final String INTERCOMPANY_AR_NOT_EXISTS = "error.intercompany_ar_not_exists";

    /**
     * 未能取到现金内部往来应付科目，请检查用途代码定义！
     */
    public static final String INTERCOMPANY_AP_NOT_EXISTS = "error.intercompany_ap_not_exists";

    /**
     * 未能取到对方核算主体缺省的汇率类型,请检查参数指定!
     */
    public static final String DEFAULT_EXCHANGE_RATE_TYPE_NOT_EXISTS = "error.default_exchange_rate_type_not_exists";

    /**
     * 未能取到对方核算主体缺省的汇率,请检查参数指定!
     */
    public static final String DEFAULT_EXCHANGE_RATE_NOT_EXISTS = "error.default_exchange_rate_not_exists";

    /**
     * 暂不支持此核销类型。
     */
    public static final String UNSUPPORT_WRITE_OFF_TYPE = "error.unsupport_write_off_type";

    public CshWriteOffException(String code, String message, Object[] parameters) {
        super(code, message, parameters);
    }

}
