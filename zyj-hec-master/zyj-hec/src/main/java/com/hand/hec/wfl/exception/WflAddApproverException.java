package com.hand.hec.wfl.exception;

import com.hand.hap.core.exception.BaseRuntimeExcepiton;

/**
 * description
 *
 * @author mouse 2019/03/11 16:08
 */
public class WflAddApproverException extends BaseRuntimeExcepiton {

    /**
     * 工作流节点动作异常.
     *
     * @param code 异常 code,通常与模块 CODE 对应
     * @param descriptionKey 异常消息代码,系统描述中定义
     * @param parameters
     */
    public WflAddApproverException(String code, String descriptionKey, Object[] parameters) {
        super(code, descriptionKey, parameters);
    }
}
