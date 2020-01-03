package com.hand.hec.wfl.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.wfl.dto.WflInsFlow;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface WflInsFlowMapper extends Mapper<WflInsFlow>{
    List<WflInsFlow> getNextFlow(@Param("fromElementType") String fromElementType, @Param("fromElementId") Long fromElementId);

    List<WflInsFlow> getPreFlow(@Param("toElementType") String toElementType, @Param("toElementId") Long toElementId);
}