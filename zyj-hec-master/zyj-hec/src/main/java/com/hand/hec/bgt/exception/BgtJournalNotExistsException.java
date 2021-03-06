package com.hand.hec.bgt.exception;

import com.hand.hap.core.exception.BaseRuntimeExcepiton;

/**
 * description
 *
 * @author mouse 2019/03/14 15:53
 */
public class BgtJournalNotExistsException extends BaseRuntimeExcepiton {

    /**
     *
     * @param code 异常 code,通常与模块 CODE 对应
     * @param descriptionKey 异常消息代码,系统描述中定义
     * @param parameters
     */
    public BgtJournalNotExistsException(String code, String descriptionKey, Object[] parameters) {
        super(code, descriptionKey, parameters);
    }
}

