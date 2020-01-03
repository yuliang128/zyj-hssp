package com.hand.hec.bgt.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.bgt.dto.BgtControlStrategyGroup;

import java.util.List;

/**
 * <p>
 * 预算控制策略组Service
 * </p>
 *
 * @author mouse 2019/01/07 16:55
 */
public interface IBgtControlStrategyGroupService
                extends IBaseService<BgtControlStrategyGroup>, ProxySelf<IBgtControlStrategyGroupService> {
    /**
     * 根据预算组织ID查询启用状态的控制策略组
     *
     * @param bgtOrgId 预算组织ID
     * @param magOrgId 管理组织ID
     * @author guiyuting 2019-03-06 18:46
     * @return 符合条件的控制策略组
     */
    List<BgtControlStrategyGroup> queryByBgtOrgId(IRequest request, Long bgtOrgId, Long magOrgId);

}
