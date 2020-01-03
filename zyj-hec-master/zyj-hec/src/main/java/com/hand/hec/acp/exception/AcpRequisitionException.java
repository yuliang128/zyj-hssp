package com.hand.hec.acp.exception;

import com.hand.hap.core.exception.BaseRuntimeExcepiton;

/**
 * description
 *
 * @author mouse 2019/03/14 15:53
 */
public class AcpRequisitionException extends BaseRuntimeExcepiton {
    public static String UNEXISTS_LINES_ERROR = "acp_requisition_line_unexists_error";
    public static String LNS_AMOUNT_ERROR = "acp_requisition_lns_amount_error";
    public static String DTL_AMOUNT_ERROR = "acp_requisition_dtl_amount_error";
    public static String USED_AMOUNT_ERROR = "acp_requisition_dtl_used_amount_error";
    public static String DOC_NUMBER_ERROR = "exp_rep_coding_rule_error";


    /**
     * 请先创建凭证
     */
    public static final String CREATE_ACCOUNT_FIRST = "error.create_account_first";

    /**
     *  凭证借贷不平衡，不能审核
     */
    public static final String LOAN_VOUCHER_NOT_BALANCE = "error.loan_voucher_not_balance";

    private static final String CODE_EXCEPTION = "ACP";

    /**
     *
     * @param code 异常 code,通常与模块 CODE 对应
     * @param descriptionKey 异常消息代码,系统描述中定义
     * @param parameters
     */
    public AcpRequisitionException(String descriptionKey, Object[] parameters) {
        super(CODE_EXCEPTION, descriptionKey, parameters);
    }
}

