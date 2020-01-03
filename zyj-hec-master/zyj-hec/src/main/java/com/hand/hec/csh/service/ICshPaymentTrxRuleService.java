package com.hand.hec.csh.service;

import java.util.List;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.csh.dto.CshPaymentTrxRule;

public interface ICshPaymentTrxRuleService
                extends IBaseService<CshPaymentTrxRule>, ProxySelf<ICshPaymentTrxRuleService> {

    /**
     * @Description 获取付款事务生成规则
     *
     * @Author Tagin
     * @Date 2019-03-13 21:27
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
     * @Return java.lang.Long
     * @Version 1.0
     **/
    Long getTrxRuleId(Long magOrgId, Long accEntityId, String documentCategory, String documentType,
                    String payeeCategory, String payeeCode, String accountNum, String paymentCurrency,
                    String paymentMethod, String paymentUsede);

    
    /**
     * 存储付款事务生成规则的同事，生成初始的六条分组规则
     *
     * @param cshPaymentTrxRuleList 付款事务生成规则
     * @param request 上下文
     * @author ngls.luhui 2019-03-21 14:59
     * @return 
     */
    Boolean submitTrx(List<CshPaymentTrxRule> cshPaymentTrxRuleList,IRequest request);
}
