package com.hand.hap.sys.exception;

import com.hand.hap.core.exception.BaseException;

/**
 * 参数定义异常.
 *
 * @author njq.niu@hand-china.com
 * @date 2016/4/25
 */
public class ParameterException extends BaseException {

    /**
     * 有效日期至不能小于有效日期从.
     */
    public static final String END_DATE_MORE_START_DATE = "error.end_date_more_start_date";
    /**
     * 至少要为此参数选择一个级别.
     */
    public static final String AT_LEAST_CHOOSE_ONE_LEVEL = "error.at_least_choose_one_level";
    private static final long serialVersionUID = 1L;

    public ParameterException(String code, String message, Object[] parameters) {
        super(code, message, parameters);
    }

}
