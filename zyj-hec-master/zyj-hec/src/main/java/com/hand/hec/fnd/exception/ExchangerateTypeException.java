package com.hand.hec.fnd.exception;

import com.hand.hap.core.exception.BaseException;

/**
 * <p>
 * 汇率类型异常
 * </p>
 *
 * @author jialin.xing@hand-china.com 2019/01/14 16:24
 */
public class ExchangerateTypeException extends BaseException {

    /**
     * 能存在一个主帐号
     */
    public static final String EXCHANGE_REF_ACC_ERROR = "exchange_ref_acc_error";

    public ExchangerateTypeException(String code, String descriptionKey, Object[] parameters) {
        super(code, descriptionKey, parameters);
    }
}
