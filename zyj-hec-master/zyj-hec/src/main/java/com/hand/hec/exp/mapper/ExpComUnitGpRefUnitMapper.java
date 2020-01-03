package com.hand.hec.exp.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.exp.dto.ExpComUnitGpRefUnit;

import java.util.List;

public interface ExpComUnitGpRefUnitMapper extends Mapper<ExpComUnitGpRefUnit> {
    /**
     * 查找该部门组所有分配信息
     *
     * @param expComUnitGpRefUnit
     * @author guiyu 2019-01-21 15:18
     * @return 部门组分配信息
     */
    List<ExpComUnitGpRefUnit> queryRefUnitInfo(ExpComUnitGpRefUnit expComUnitGpRefUnit);
}
