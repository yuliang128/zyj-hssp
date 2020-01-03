package com.hand.hec.wfl.mapper;

import com.hand.hap.core.IRequest;
import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.wfl.dto.WflFlow;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface WflFlowMapper extends Mapper<WflFlow>{
    public List<WflFlow> processFlowQuery(@Param("processId") Long processId, IRequest request);
}