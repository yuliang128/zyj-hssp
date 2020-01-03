package com.hand.hec.bgt.service;

import java.util.List;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.bgt.dto.BgtBudgetItemType;

/**
 * <p>
 * 预算项目类型Service
 * </p>
 *
 * @author mouse 2019/01/07 16:53
 */
public interface IBgtBudgetItemTypeService
        extends IBaseService<BgtBudgetItemType>, ProxySelf<IBgtBudgetItemTypeService> {

    /**
     * 预算检查的预算项目类型符合判断
     *
     * @param controlType 过滤类型，明细、汇总、全部
     * @param bgtOrgId 预算组织ID
     * @param itemTypeId 当前占用行预算项目类型ID
     * @param filtrateMethod 控制规则取值方式
     * @param controlItemTypeCodeFrom 控制规则预算项目类型代码从
     * @param controlItemTypeCodeTo 控制规则预算项目类型代码到
     * @author mouse 2019-01-10 15:50
     * @return 符合的预算项目类型
     */
    List<BgtBudgetItemType> checkBgtItemType(String controlType, Long bgtOrgId, Long itemTypeId, String filtrateMethod,
            String controlItemTypeCodeFrom, String controlItemTypeCodeTo);

    /**
     * 根据预算组织ID 查询预算项目类型信息
     *
     * @param bgtBudgetItemType
     * @author guiyuting 2019-03-11 17:01
     * @return 符合条件的预算项目类型信息
     */
    List<BgtBudgetItemType> queryByBgtOrgId(IRequest request,BgtBudgetItemType bgtBudgetItemType);
}
