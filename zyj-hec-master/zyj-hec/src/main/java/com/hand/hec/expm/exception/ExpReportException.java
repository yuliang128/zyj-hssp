package com.hand.hec.expm.exception;

import com.hand.hap.core.exception.BaseRuntimeExcepiton;

/**
 * 报销单对应的异常
 *
 * @author yang.duan 2019-5-28 11:18:42
 */
public class ExpReportException extends BaseRuntimeExcepiton {
    private static final long serialVersionUID = 1L;
    /*请先维护部门下的默认成本中心,再重新选择该部门,保存单据!*/
    public static final String EXP5110_DFT_RESP_CENTER_IS_NULL_ERROR = "error.dft_resp_center_is_null_error";

    /*获取单据编号出错，请检查编码规则定义。*/
    public static final String EXP5110_GET_DOCUMENT_NUMBER_ERROR = "error.get_document_number_error";


    /*至少存在一条行信息,提交失败!*/
    public static final String EXP5110_EXP_REP_DISTS_NULL_ERROR = "error.exp_rep_dists_null_error";

    /*调整类的报销单合计金额必须为零！*/
    public static final String EXP5110_EXP_REP_ADJUSTMENT_ZERO_ERROR = "error.exp_rep_adjustment_zero_error";

    /*分配行金额合计不等于报销单行金额合计!*/
    public static final String EXP5110_EXP_REP_LINES_REPORT_AMOUNT_ERROR = "error.exp_rep_lines_report_amount_error";


    /*费用报销单行总金额与计划付款行总金额必须相同。*/
    public static final String EXP5110_EXP_REP_PMT_CONTRACT_TOTAL_AMOUNT_ERR = "error.exp_rep_pmt_contract_total_amount_err";

    /*当前收款方下存在未核销完的借款,请先到计划付款中核销借款!*/
    public static final String EXP5110_EXP_REP_CSH_WRITE_OFF_ASK_ERROR = "error.exp_rep_csh_write_off_ask_error";

    /*报销单整单金额" + reportAmount + "超出申请单金额*/



    /*报销单" + lineNum + "行中，存在计划付款行核销借款金额大于到期金额！*/




    /*存在收款方银行账户为空的付款行信息，请检查！*/
    public static final String EXP5110_EXP_REP_PMT_ACCOUNT_NULL_ERROR = "error.exp_rep_pmt_account_null_error";

    /*行号不能重复!*/
    public static final String EXP5110_EXP_REP_LINE_NUMBER_ERROR = "error.exp_rep_line_number_error";


    /*维值错误！不属于此维度！*/
    public static final String EXP5110_DIMENSION_VALUE_ERROR = "error.dimension_value_error";


    /*非调整类型的报销单，数量和金额必须为正数！*/
    public static final String EXP5110_EXP_REP_POSITIVE_NUMBER_ERROR = "error.exp_rep_positive_number_error";

    /*金额和数量不能为零！*/
    public static final String EXP5110_EXP_REP_ZERO_CHECK_ERROR = "error.exp_rep_zero_check_error";

    /*此报销单行已经被删除!*/
    public static final String EXP5110_EXP_REP_EXPENSE_LINE_DELETED_ERROR = "error.exp_rep_expense_line_deleted";

    /*该计划支付行已发生核销,不能修改收款方信息!*/
    public static final String EXP5110_PMT_SCHEDULE_LN_OCCUR_WF_ERROR = "error.pmt_schedule_ln_occur_wf_error";



    public ExpReportException(String code, String message, Object[] parameters) {
        super(code, message, parameters);
    }
}
