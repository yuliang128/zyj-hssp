package com.hand.hec.fnd.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: MouseZhou
 * \* Date: 2018/3/27
 * \* Time: 15:43
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */

public interface FndBusinessRuleEngineMapper {
    List<HashMap> getConditionValue(@Param("sqlContent") String sqlContent);
}