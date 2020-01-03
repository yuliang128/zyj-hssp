package com.hand.hec.wfl.mapper;

import com.hand.hap.core.IRequest;
import com.hand.hap.mybatis.common.Mapper;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hec.wfl.dto.WflEvent;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface WflEventMapper extends Mapper<WflEvent>{
    public List<WflEvent> processEventQuery(@Param("processId") Long processId, IRequest request);
}