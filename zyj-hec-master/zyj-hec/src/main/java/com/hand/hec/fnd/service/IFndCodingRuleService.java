package com.hand.hec.fnd.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.fnd.dto.FndCodingRule;
import com.hand.hec.fnd.dto.FndCodingRuleObject;

import java.util.List;

public interface IFndCodingRuleService extends IBaseService<FndCodingRule>, ProxySelf<IFndCodingRuleService>{


    /**
     *
     * @param request
     * @param dto
     * @param page
     * @param pageSize
     * @return 返回合适的编码规则
     * @author zhongyu 2019-2-22 16:36
     */
    List<FndCodingRule> queryByCodingRuleObjectId(IRequest request, FndCodingRule dto,int page,int pageSize);

}