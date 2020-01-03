package com.hand.hec.csh.exception;

import com.hand.hap.core.exception.BaseException;

/**
 * 参数定义异常.
 *
 * @author njq.niu@hand-china.com
 * @date 2016/4/25
 */
public class CshPaymentRequisitionHdException extends BaseException {

    private static final long serialVersionUID = 1L;

    /**
     * 单据状态不能提交
     */
    public static final String STATUS_CANNOT_SUBMIT = "error.status_cannot_submit";

    /**
     * 借款申请行金额合计不等于借款总金额，不能提交!
     */
    public static final String HEAD_AMOUNT_NOT_EQUAL_LINE_AMOUNT = "error.head_amount_not_equal_line_amount";

    /**
     * 借款申请单申请金额不能大于费用申请单的未申请借款金额！
     */
    public static final String PAYMENT_AMOUNT_NOT_MORE_REQ_AMOUNT = "error.payment_amount_not_more_req_amount";

    /**
     * 只有新建 收回和拒绝的单据才能删除
     */
    public static final String DOCUMENT_STATUS_DELETE_ERROR = "error.document_status_delete_error";

    /**
     * 未找到该条借款申请单
     */
    public static final String PAYMENT_REQUISITION_NOT_FOUND = "error.payment_requisition_not_found";

    /**
     * 非审批完成状态的借款申请单不可关闭, 请确认!
     */
    public static final String DOCUMENT_NOT_APPROVED_CANNOT_CLOSE = "error.document_not_approved_cannot_close";

    /**
     *单据状态不匹配，请检查单据状态!
     */
    public static final String SSC_DOC_STATUS_NOT_EQUAL = "error.ssc_doc_status_not_equal";

	/**
	 *借款申请单已经被反冲！
	 */
	public static final String CSH5080_REVERSE_FLAG_ERROR = "error.csh5080_reverse_flag_error";

	/**
	 *借款申请单还未审核！
	 */
	public static final String CSH5080_AUDIT_FLAG_ERROR = "error.csh5080_audit_flag_error";

	/**
	 *反冲日期必须在打开的会计期间内！
	 */
	public static final String EXP5150_REVERSE_DATE_ERROR = "error.exp5150_reverse_date_error";

    public CshPaymentRequisitionHdException(String code, String message, Object[] parameters) {
        super(code, message, parameters);
    }

}
