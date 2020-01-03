package com.hand.hec.fnd.mapper;

import com.hand.hap.core.IRequest;
import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.fnd.dto.FndUnitDimAssign;

import java.util.List;

public interface FndUnitDimAssignMapper extends Mapper<FndUnitDimAssign>{
    List<FndUnitDimAssign> queryByUnit(FndUnitDimAssign fndUnitDimAssign);
}