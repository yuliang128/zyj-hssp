package com.hand.hec.wfl.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.wfl.dto.WflInsGateway;
import org.apache.ibatis.annotations.Param;

public interface WflInsGatewayMapper extends Mapper<WflInsGateway>{
    WflInsGateway selectInsGatewayById(@Param("insGatewayId") Long insGatewayId);
}