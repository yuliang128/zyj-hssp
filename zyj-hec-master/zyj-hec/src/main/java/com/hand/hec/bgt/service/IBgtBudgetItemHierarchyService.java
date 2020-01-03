package com.hand.hec.bgt.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.bgt.dto.BgtBudgetItemHierarchy;

import java.util.List;

/**
 * <p>
 * 预算项目层次Service
 * </p>
 * 
 * @author mouse 2019/01/07 16:53
 */
public interface IBgtBudgetItemHierarchyService
                extends IBaseService<BgtBudgetItemHierarchy>, ProxySelf<IBgtBudgetItemHierarchyService> {

    /**
     * 根据parentItemId查询预算项目层次信息
     *
     * @param budgetItemHierarchy
     * @author guiyuting 2019-03-13 15:35
     * @return 符合条件的预算项目层次信息
     */
    List<BgtBudgetItemHierarchy> queryByParentItem(IRequest request, BgtBudgetItemHierarchy budgetItemHierarchy,
                    int page, int pageSize);

}
