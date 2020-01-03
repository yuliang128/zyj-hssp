package com.hand.hec.gld.exception;

import com.hand.hap.core.exception.BaseException;

/**
 * 参数定义异常.
 *
 * @author njq.niu@hand-china.com
 * @date 2016/4/25
 */
public class GldSetOfBookException extends BaseException {
    private static final long serialVersionUID = 1L;
    /**
     * 不能失效已被核算主体关联的账套
     */
    public static final String FAILURE_ACCOUNT_SET = "failure_account_set";
    /**
     * 该账套已被关联管理组织,不能失效!
     */
    public static final String CANNOT_BE_INVALIDATED = "cannot_be_invalidated";

    public GldSetOfBookException(String code, String message, Object[] parameters) {
        super(code, message, parameters);
    }
}
