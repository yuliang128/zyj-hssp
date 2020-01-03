package com.hand.hec.wfl.exception;

import com.hand.hap.core.exception.BaseException;

/**
 * description
 *
 * @author mouse 2019/02/27 19:15
 */
public class WflProcessGenerateException extends BaseException {

    /**
     * 工作流生成异常.
     *
     * @param code 异常 code,通常与模块 CODE 对应
     * @param descriptionKey 异常消息代码,系统描述中定义
     * @param parameters
     */
    public WflProcessGenerateException(String code, String descriptionKey, Object[] parameters) {
        super(code, descriptionKey, parameters);
    }
}
