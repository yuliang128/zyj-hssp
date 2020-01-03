package com.hand.hec.exp.exception;

import com.hand.hap.core.exception.BaseRuntimeExcepiton;

/**
 * description
 *
 * @author zhaohui 2019/03/29 16:18
 */
public class ExpRequisitionException extends BaseRuntimeExcepiton {

    /**
     *关联的报销单存在未审核状态，申请单不能关闭！
     **/
    public static String EXP_REQ_CLOSED_CHECK_EXP_REPORT_STATUS_ERROR = "error.exp_req_closed_check_exp_report_status_error";

    public ExpRequisitionException(String code, String message, Object[] parameters) {
            super(code,message,parameters);
    }
}
