package com.hand.hec.fnd.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.fnd.dto.FndUnitDimValueAssign;

import java.util.List;

public interface FndUnitDimValueAssignMapper extends Mapper<FndUnitDimValueAssign>{
    /**
     * 根据部门分配维度ID查询 部门分配维值信息
     *
     * @param fndUnitDimValueAssign
     * @author guiyuting 2019-03-27 14:21
     * @return 符合条件的部门分配维值信息
     */
    List<FndUnitDimValueAssign> queryByDimAssignId(FndUnitDimValueAssign fndUnitDimValueAssign);

}