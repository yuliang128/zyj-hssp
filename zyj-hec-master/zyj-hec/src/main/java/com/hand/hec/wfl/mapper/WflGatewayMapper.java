package com.hand.hec.wfl.mapper;

import com.hand.hap.core.IRequest;
import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.wfl.dto.WflGateway;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface WflGatewayMapper extends Mapper<WflGateway>{
    public List<WflGateway> processGatewayQuery(@Param("processId") Long processId, IRequest request);
}