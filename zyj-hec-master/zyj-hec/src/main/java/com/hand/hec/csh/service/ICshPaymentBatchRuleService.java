package com.hand.hec.csh.service;

import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.csh.dto.CshPaymentBatchRule;

public interface ICshPaymentBatchRuleService
                extends IBaseService<CshPaymentBatchRule>, ProxySelf<ICshPaymentBatchRuleService> {

    /**
     * 获取付款批类型ID
     *
     * @author Tagin
     * @date 2019-04-03 20:37
     * @param magOrgId 管理组织ID
     * @param accEntityId 核算主体ID
     * @param documentCategory 单据类别
     * @param documentType 单据类型代码
     * @param payeeCategory 收款方类别
     * @param payeeCode 收款方
     * @param accountNum 付款账户
     * @param paymentCurrency 付款币种
     * @param paymentMethod 付款方式
     * @param paymentUsede 付款用途
     * @return java.lang.Long
     * @version 1.0
     **/
    Long getTypeId(Long magOrgId, Long accEntityId, String documentCategory, String documentType, String payeeCategory,
                    String payeeCode, String accountNum, String paymentCurrency, String paymentMethod,
                    String paymentUsede);
}
