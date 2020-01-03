package com.hand.hec.wfl.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.wfl.dto.WflInsTaskHierarchy;

import java.util.List;

public interface WflInsTaskHierarchyMapper extends Mapper<WflInsTaskHierarchy>{
    List<WflInsTaskHierarchy> selectReachableHierarchy(Long insTaskId);
}