package com.hand.hec.ssc.exception;

import com.hand.hap.core.exception.BaseException;

/**
 * 不能互为上下级异常
 *
 * @author bo.zhang05@hand-china.com
 * @date 2019/03/19
 */
public class SscWorkTeamException extends BaseException {
    private static final long serialVersionUID = 1L;
    /**
     * 不能互为上下级
     */
    public static final String NOT_MUTUAL_SUPERIOR = "not_mutual_superior";

    public SscWorkTeamException(String code, String message, Object[] parameters) {
        super(code, message, parameters);
    }
}
