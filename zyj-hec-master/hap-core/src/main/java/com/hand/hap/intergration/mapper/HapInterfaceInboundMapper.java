package com.hand.hap.intergration.mapper;

import java.util.List;

import com.hand.hap.intergration.dto.HapInterfaceInbound;
import com.hand.hap.mybatis.common.Mapper;

public interface HapInterfaceInboundMapper extends Mapper<HapInterfaceInbound>{

    List<HapInterfaceInbound> select (HapInterfaceInbound inbound);
}