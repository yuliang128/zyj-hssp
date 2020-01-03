package com.hand.hec.csh.exception;

import com.hand.hap.core.exception.BaseRuntimeExcepiton;

/**
 * <p>
 * 核销相关的错误消息
 * </p>
 *
 * @author luhui 2019/03/11 10:21
 */
public class CshTransactionException extends BaseRuntimeExcepiton {

    private static final long serialVersionUID = 1L;

    /**
     * 单据已被其他用户删除，请返回。
     */
    public static final String TRX_NOT_FOUND_ERROR = "error.TRX_NOT_FOUND_ERROR";

    /**
     * 此付款已被完全核销，不能再进行核销。
     */
    public static final String WRITE_OFF_COMPLETED_ERROR = "error.write_off_completed_error";

    /**
     * 此付款已被完全退款，不能再进行核销。
     */
    public static final String RETURNED_COMPLETED_ERROR = "error.returned_completed_error";

    /**
     * 此付款已被冲销，不能再进行核销。
     */
    public static final String REVERSED_ERROR = "error.reversed_error";

    /**
     * 此付款已被过账，不能再进行核销。
     */
    public static final String TRX_POSTED_ERROR = "error.trx_posted_error";

    /**
     * 事务期间已经关闭不能执行过账操作!
     */
    public static final String PERIOD_NOT_OPEN_ERROR = "error.period_not_open_error";

    /**
     * 此单据正在被处理，请稍等！
     */
    public static final String TRX_LOCKED_ERROR = "error.trx_locked_error";

    /**
     * 未查到资金-现金事务关联行!
     */
    public static final String CASH_PMT_NOT_FOUND_ERROR = "error.cash_pmt_not_found_error";

    /**
     * 总金额为零,不存在资金计划行
     */
    public static final String CASH_PMT_ZERO_ERROR = "error.cash_pmt_zero_error";

    /**
     * 现金事务与合同资金总金额不相等！
     */
    public static final String CASH_NOT_EQUAL_PMT_ERROR = "error.cash_not_equal_pmt_error";

    /**
     * 付款事务收款方已变更，资金计划行收款方与付款事务收款方不一致，请修改。
     */
    public static final String CON_PAYEE_ERROR = "error.con_payee_error";

    /**
     * 付款币种已变更，资金计划行币种与付款币种不一致，请修改。
     */
    public static final String CON_CURRENCY_ERROR = "error.con_currency_error";

    /**
     * 付款币种已变更，资金计划行币种与付款币种不一致，请修改。
     */
    public static final String REQ_PAYEE_ERROR = "error.req_payee_error";

    /**
     * 付款币种已变更，资金计划行币种与付款币种不一致，请修改。
     */
    public static final String REQ_CURRENCY_ERROR = "error.req_currency_error";

    /**
     * 未能获取现金内部往来应收科目，请检查用途代码定义！
     */
    public static final String CSH_INTERCOMPANY_AR_NOT_FOUND = "error.csh_intercompany_ar_not_found";

    /**
     * 未能获取现金内部往来应付科目，请检查用途代码定义！
     */
    public static final String CSH_INTERCOMPANY_AP_NOT_FOUND = "error.csh_intercompany_ap_not_found";

    /**
     * 未能获取对方核算主体缺省汇率类型，请检查参数指定！
     */
    public static final String DFT_TO_EXCHANGE_RATE_TYPE_NOT_FOUND = "error.dft_to_exchange_rate_type_not_found";

    /**
     * 未能获取对方核算主体缺省汇率类型下的汇率！
     */
    public static final String DFT_TO_EXCHANGE_RATE_NOT_FOUND = "error.dft_to_exchange_rate_not_found";

    /**
     * 未能获取银行账户的科目或成本中心，请检查银行账户定义！
     */
    public static final String BANK_ACCOUNT_NOT_FUND = "error.bank_account_not_fund";

    /**
     * 单据编号规则无法取得不重复的单据编号，请更换单据编号规则！
     */
    public static final String TRX_CODING_RULE_ERROR = "error.trx_coding_rule_error";

    /**
     * 未能获取预付款科目，请检查用途代码定义！
     */
    public static final String PREPAYMENT_NOT_FOUND = "error.prepayment_not_found";

    /**
     * 未能获取付款批类型，请先配置付款批决定规则或默认付款批类型参数！
     */
    public static final String PAYMENT_BATCH_NOT_FOUND = "error.payment_batch_not_found";

    /**
     * 未能获取付方银行账户信息，请先维护银行信息！
     */
    public static final String PAYMENT_ACCOUNT_NOT_FOUND = "error.payment_account_not_found";

    /**
     * 未能获取收方银行账户信息，请先维护银行信息！
     */
    public static final String PAYEE_ACCOUNT_NOT_FOUND = "error.payee_account_not_found";

    /**
     * 原事务已经全额退款, 不能再执行退款操作！
     */
    public static final String RETURN_COMPLETE_ERROR = "error.return_complete_error";

    /**
     * 原事务已经被冲销，不能再进行退款！
     */
    public static final String RETURN_REVERSED_ERROR = "error.return_reversed_error";

    /**
     * 退款事务的日期不能早于原事务的日期！
     */
    public static final String RETURN_DATE_ERROR = "error.return_date_error";

    /**
     * 未能取得当前公司的帐套,请检查公司及账套定义！
     */
    public static final String SET_OF_BOOKS_ERROR = "error.set_of_books_error";

    /**
     * 凭证借贷金额不相等，不能过帐！
     */
    public static final String DR_CR_AMOUNT_ERROR = "error.dr_cr_amount_error";

    /**
     * 未能获取核算主体的默认帐套，请检查核算主体定义！
     */
    public static final String DFT_SET_OF_BOOKS_NOT_FOUND = "error.dft_set_of_books_not_found";

    /**
     * 未能获取付款申请单审核凭证的科目，请检查系统配置！
     */
    public static final String ACP_AUDIT_ACCOUNT_NOT_FOUND = "error.acp_audit_account_not_found";

    /**
     * 未能获取借款申请单审核凭证的科目，请检查系统配置！
     */
    public static final String CSH_AUDIT_ACCOUNT_NOT_FOUND = "error.csh_audit_account_not_found";

    /**
     * 未能获取付款单科目，请检查用途代码定义！
     */
    public static final String PAY_REQUISITION_NOT_FOUND = "error.pay_requisition_not_found";

    /**
     * 未能获取付款申请单支付凭证的科目，请检查系统配置！
     */
    public static final String ACP_PAYMENT_ACCOUNT_NOT_FOUND = "error.acp_payment_account_not_found";

    /**
     * 原事务已被退款,不能再进行反冲！
     */
    public static final String REVERSED_RETURN_ERROR = "error.reversed_return_error";

    /**
     * 原事务已被反冲,不能再进行反冲！
     */
    public static final String REVERSED_COMPLETE_ERROR = "error.reversed_complete_error";

    /**
     * 付款事务对应的预付款事务已被核销，不能进行反冲！
     */
    public static final String PREPAYMENT_WRITE_OFF_ERROR = "error.prepayment_write_off_error";

    /**
     * 反冲日期不能早于原事务的日期！
     */
    public static final String REVERSED_DATE_ERROR = "error.reversed_date_error";

    /**
     * 反冲日期所在期间未打开或已关闭，不能进行反冲！
     */
    public static final String REVERSED_PERIOD_NOT_OPEN_ERROR = "error.reversed_period_not_open_error";

    /**
     * 原报销单已重新支付，不能再进行反冲！
     */
    public static final String REVERSED_REPORT_COMPLETE_ERROR = "error.reversed_report_complete_error";

    /**
     * 原借款单已重新支付，不能再进行反冲！
     */
    public static final String REVERSED_PAYMENT_COMPLETE_ERROR = "error.reversed_payment_complete_error";

    public CshTransactionException(String code, String message, Object[] parameters) {
        super(code, message, parameters);
    }

}
