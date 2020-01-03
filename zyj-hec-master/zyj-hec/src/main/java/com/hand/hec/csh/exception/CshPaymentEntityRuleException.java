package com.hand.hec.csh.exception;

import com.hand.hap.core.exception.BaseException;

/**
 * 参数定义异常.
 *
 * @author njq.niu@hand-china.com
 * @date 2016/4/25
 */
public class CshPaymentEntityRuleException extends BaseException {

    private static final long serialVersionUID = 1L;

    /**
     * "业务类型、优先级、支付主体"这样的组合已存在,请检查!
     */
    public static final String PAYMENT_ENTITY_RULE_ONE_DUPLICATE = "error.payment_entity_rule_one_duplicate";

    public CshPaymentEntityRuleException(String code, String message, Object[] parameters) {
        super(code, message, parameters);
    }

}
