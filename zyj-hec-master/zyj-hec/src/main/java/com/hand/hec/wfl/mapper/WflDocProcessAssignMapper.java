package com.hand.hec.wfl.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.wfl.dto.WflDocProcessAssign;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface WflDocProcessAssignMapper extends Mapper<WflDocProcessAssign>{

    public List<WflDocProcessAssign> queryAssignByDocInfo(@Param("docCategory") String docCategory, @Param("docTypeId") Long docTypeId);

    List<WflDocProcessAssign> queryWflDocProcessAssign(WflDocProcessAssign wflDocProcessAssign);
}