package com.hand.hec.expm.exception;

import com.hand.hap.core.exception.BaseRuntimeExcepiton;

/**
 * 报销单创建凭证对应的异常
 *
 * @author zhaohui 2019/02/28 16:45
 */
public class ExpReportAccountException extends BaseRuntimeExcepiton {
    private static final long serialVersionUID = 1L;

    /**
     * 未能获取用途代码为 [ EMPLOYEE_EXPENSE ] 的科目，请检查科目配置！
     */
    public static final String EXP5140_GET_EMPLOYEE_EXPENSE_ACC_ERROR = "error.exp5140_get_employee_expense_acc_error";

    /**
     * 未取到公司缺省成本中心!
     */
    public static final String EXP5140_RESP_CENTER_ERROR = "error.exp5140_resp_center_error";

    /**
     * 没取到用途代码为[INTERCOMPANY_AR]的科目，请检查科目配置!
     */
    public static final String EXP5140_GET_INTERCOMPANY_AR_ACC_ERROR = "error.exp5140_get_intercompany_ar_acc_error";

    /**
     * 没取到用途代码为[INTERCOMPANY_AP]的科目，请检查科目配置!
     */
    public static final String EXP5140_GET_INTERCOMPANY_AP_ACC_ERROR = "error.exp5140_get_intercompany_ap_acc_error";

    /**
     * 没取到用途代码为[EMPLOYEE_EXPENSE_CLEARING]的科目,请检查科目配置!
     */
    public static final String EXP5140_GET_EXP_CLE_ACC_ERROR = "error.exp5140_get_exp_cle_acc_error";

    /**
     * 没取到用途代码为[EMPLOYEE_EXPENSE_CLEARING_TAX]的科目,请检查科目配置!
     */
    public static final String EXP5140_GET_EXP_CLE_TAX_ACC_ERROR = "error.exp5140_get_exp_cle_tax_acc_error";

    /**
     * 没取到报销费用税金的科目，请检查科目配置!
     */
    public static final String EXP5140_GET_EMPLOYEE_EXPENSE_TAX_ACC_ERROR = "error.exp5140_get_employee_expense_tax_acc_error";

    /**
     * 未取到非全税额入账转出的科目，请检查科目配置!
     */
    public static final String EXP5140_GET_TAX_TRANSFER_OUT_ACC_ERROR = "error.exp5140_get_tax_transfer_out_acc_error";

    /**
     * 本次核销金额超过了未核销金额！
     */
    public static final String EXP5140_GET_CURRENCY_EXCHANGE_ACC_ERROR = "error.exp5140_get_currency_exchange_acc_error";

    /**
     * 本次核销金额超过了未核销金额！
     */
    public static final String VAT1028_GET_VAT_INVOICE_ACC_ERROR = "error.vat1028_get_vat_invoice_acc_error";

    /**
     * 凭证日期不在打开的会计期间内，请重新选择凭证日期！
     */
    public static final String CSH5070_JOURNAL_DATE_ERROR = "error.csh5070_journal_date_error";

    /**
     * 所选日期对应期间未打开!
     */
    public static final String EXP5140_JOURNAL_DATE_ERROR = "error.exp5140_journal_date_error";

    /**
     * 请先创建凭证!
     */
    public static final String EXP5140_JE_CREATION_FIRST = "error.exp5140_je_creation_first";

    /**
     * 您所创建的凭证期间不在打开期间范围内!
     */
    public static final String EXP5140_PERIOD_NOT_OPEN = "error.exp5140_period_not_open";

    /**
     * 凭证借贷不平衡，不能审核!
     */
    public static final String EXP_REP_ACCOUNT_AMOUNT_BALANCE_ERROR = "error.exp_rep_account_amount_balance_error";

    public ExpReportAccountException(String code, String message, Object[] parameters) {
        super(code, message, parameters);
    }
}
