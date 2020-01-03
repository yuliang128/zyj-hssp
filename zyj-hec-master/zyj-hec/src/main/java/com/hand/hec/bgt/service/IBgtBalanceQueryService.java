package com.hand.hec.bgt.service;

import java.util.List;

import com.hand.hap.core.IRequest;
import com.hand.hec.bgt.dto.BgtBalanceQueryCondition;
import com.hand.hec.bgt.dto.BgtBalanceQueryGroup;
import com.hand.hec.bgt.dto.BgtBalanceQueryResult;

/**
 * <p>
 * 预算余额查询service
 * </p>
 *
 * @author YHL 2019/03/14 10:53
 */
public interface IBgtBalanceQueryService {

    /**
     * 针对某一组预算查询条件进行预算余额查询
     *
     * @param request 请求
     * @param queryGroup 查询条件组
     * @author YHL 2019-03-22 10:22
     * @return java.util.List<com.hand.hec.bgt.dto.BgtBalanceQueryResult> 预算余额查询结果
     */
    void doQuery(IRequest request, BgtBalanceQueryGroup queryGroup);
}
