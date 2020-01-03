package com.hand.hec.fnd.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.fnd.dto.FndCodingRuleDetail;

import java.util.List;

public interface IFndCodingRuleDetailService extends IBaseService<FndCodingRuleDetail>, ProxySelf<IFndCodingRuleDetailService>{

    /**
     * 按照顺序号排序查询数据
     * @param request
     * @param dto
     * @param page
     * @param pageSize
     * @return  编码规则明细
     * @author zhongyu 2019-2-25 15:11
     */
    List<FndCodingRuleDetail> queryBySequence (IRequest request,FndCodingRuleDetail dto,int page,int pageSize);
}