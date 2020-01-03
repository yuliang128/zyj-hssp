package com.hand.hec.csh.service;

import java.util.List;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.csh.dto.CshPaymentTrxRule;
import com.hand.hec.csh.dto.CshPaymentTrxRuleDtl;
import com.hand.hec.csh.exception.CshPaymentTrxRuleDtlException;

public interface ICshPaymentTrxRuleDtlService
                extends IBaseService<CshPaymentTrxRuleDtl>, ProxySelf<ICshPaymentTrxRuleDtlService> {


    /**
     * 增加了检查分组规则是否重复的插入方法
     *
     * @param request
     * @param list
     * @author ngls.luhui 2019-03-04 16:42
     * @return List
     */
    List<CshPaymentTrxRuleDtl> batchSubmit(IRequest request, List<CshPaymentTrxRuleDtl> list)
                    throws CshPaymentTrxRuleDtlException;


    /**
     * 获取规则明细中的分组条件
     *
     * @Author Tagin
     * @Date 2019-03-19 12:35
     * @param iRequest 请求
     * @param ruleId 付款事务生成规则ID
     * @Return java.util.List<com.hand.hec.csh.dto.CshPaymentTrxRuleDtl>
     * @Version 1.0
     **/
    List<CshPaymentTrxRuleDtl> queryCondition(IRequest iRequest, Long ruleId);

}
