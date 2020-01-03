package com.hand.hec.exp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.exp.dto.ExpOrgUnitLevel;


import java.util.List;


/**
 * <p>
 * ExpOrgUnitLevelMapper
 * </p>
 *
 * @author yang.duan 2019/01/10 11:17
 */
public interface ExpOrgUnitLevelMapper extends Mapper<ExpOrgUnitLevel> {

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
    List<ExpOrgUnitLevel> checkExpOrgUnitLevel(@Param("controlType") String controlType, @Param("unitId") Long unitId,
            @Param("filtrateMethod") String filtrateMethod,
            @Param("controlUnitLevelCodeFrom") String controlUnitLevelCodeFrom,
            @Param("controlUnitLevelCodeTo") String controlUnitLevelCodeTo);
}
