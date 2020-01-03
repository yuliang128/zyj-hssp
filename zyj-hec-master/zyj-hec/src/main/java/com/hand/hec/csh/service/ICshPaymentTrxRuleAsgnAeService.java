package com.hand.hec.csh.service;

import java.util.List;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.csh.dto.CshPaymentTrxRuleAsgnAe;

public interface ICshPaymentTrxRuleAsgnAeService extends IBaseService<CshPaymentTrxRuleAsgnAe>, ProxySelf<ICshPaymentTrxRuleAsgnAeService>{
    
    
    /**
     * 管理组织级-付款事务生成规则定义-分配付款核算主体-批量分配 查询可分配的核算主体
     *
     * @param magOrgId 管理组织id
     * @param ruleId 事务生成规则id
     * @param request
     * @param page
     * @param pageSize
     * @author ngls.luhui 2019-03-04 15:27
     * @return 
     */
    List<CshPaymentTrxRuleAsgnAe> queryCanAsgn(Long magOrgId, Long ruleId, IRequest request,Integer page, Integer pageSize);
}