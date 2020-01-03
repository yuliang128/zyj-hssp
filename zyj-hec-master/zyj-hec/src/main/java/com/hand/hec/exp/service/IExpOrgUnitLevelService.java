package com.hand.hec.exp.service;

import java.util.List;

import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.exp.dto.ExpOrgUnitLevel;


/**
 * <p>
 * IExpOrgUnitLevelService
 * </p>
 *
 * @author yang.duan 2019/01/10 11:19
 */
public interface IExpOrgUnitLevelService extends IBaseService<ExpOrgUnitLevel>, ProxySelf<IExpOrgUnitLevelService> {

    /**
     * 预算检查的部门级别符合判断
     *
     * @param controlType 过滤类型，明细、汇总、全部
     * @param unitId 当前占用行部门ID
     * @param filtrateMethod 控制规则取值方式
     * @param controlUnitLevelCodeFrom 控制规则部门级别代码从
     * @param controlUnitLevelCodeTo 控制规则部门级别代码到
     * @author mouse 2019-01-10 15:50
     * @return 符合的部门级别
     */
    List<ExpOrgUnitLevel> checkExpOrgUnitLevel(String controlType, Long unitId, String filtrateMethod,
            String controlUnitLevelCodeFrom, String controlUnitLevelCodeTo);
}
