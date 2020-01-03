package com.hand.hap.intergration.mapper;

import java.util.List;

import com.hand.hap.intergration.dto.HapInterfaceOutbound;
import com.hand.hap.mybatis.common.Mapper;

public interface HapInterfaceOutboundMapper extends Mapper<HapInterfaceOutbound>{

    List<HapInterfaceOutbound> select (HapInterfaceOutbound outbound);
}