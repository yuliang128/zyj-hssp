package com.hand.hec.exp.service;

import java.util.List;

import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.exp.dto.ExpMoUnitGroup;

/**
 * <p>
 * IExpMoUnitGroupService
 * </p>
 *
 * @author yang.duan 2019/01/10 11:26
 */
public interface IExpMoUnitGroupService extends IBaseService<ExpMoUnitGroup>, ProxySelf<IExpMoUnitGroupService> {

    /**
     * 预算检查的部门组符合判断
     *
     * @param controlType 过滤类型，明细、汇总、全部
     * @param unitId 当前占用行部门ID
     * @param filtrateMethod 控制规则取值方式
     * @param controlUnitGroupCodeFrom 控制规则部门组代码从
     * @param controlUnitGroupCodeTo 控制规则部门组代码到
     * @author mouse 2019-01-10 15:50
     * @return 符合的部门组
     */
    List<ExpMoUnitGroup> checkExpOrgUnitGroup(String controlType, Long unitId, String filtrateMethod,
            String controlUnitGroupCodeFrom, String controlUnitGroupCodeTo);
}
