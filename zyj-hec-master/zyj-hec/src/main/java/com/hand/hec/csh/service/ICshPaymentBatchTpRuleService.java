package com.hand.hec.csh.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.csh.dto.CshPaymentBatchTpRule;

import java.util.List;

public interface ICshPaymentBatchTpRuleService extends IBaseService<CshPaymentBatchTpRule>, ProxySelf<ICshPaymentBatchTpRuleService>{
    /**
     *查询付款批合并规则
     *
     *@Author hui.zhao01@hand-china.com
     *@Date 2019/3/4 18:58
     *@param iRequest 请求上下文
     *@param dto 查询条件
     *@param page
     *@param pageSize
     *@return List<CshPaymentBatchTpRule>
     *@Version 1.0
     **/
    List<CshPaymentBatchTpRule> query(IRequest iRequest,CshPaymentBatchTpRule dto, int page, int pageSize);
}