package com.hand.hec.acp.exception;

import com.hand.hap.core.exception.BaseRuntimeExcepiton;

/**
 * description
 *
 * @author mouse 2019/03/14 15:53
 */
public class AcpRequisitionAuditException extends BaseRuntimeExcepiton {

    public static String NEED_EXCHANGE_RATE = "exp5140_need_exchange_rate";

    public static String PERIOD_NAME = "csh5070_journal_date_error";

    public static String ACP_GET_ACP_REQUISITION_ERROR = "acp_get_acp_requisition_error";

    public static String EXP5140_RESP_CENTER_ERROR = "exp5140_resp_center_error";

    private static final String CODE_EXCEPTION = "ACP";

    /**
     *
     * @param code 异常 code,通常与模块 CODE 对应
     * @param descriptionKey 异常消息代码,系统描述中定义
     * @param parameters
     */
    public AcpRequisitionAuditException(String descriptionKey, Object[] parameters) {
        super(CODE_EXCEPTION, descriptionKey, parameters);
    }
}

