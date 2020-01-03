package com.hand.hec.wfl.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.wfl.dto.WflInstance;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface WflInstanceMapper extends Mapper<WflInstance>{
    List<Map> getArrivedElement(@Param("instanceId") Long instanceId);

    List<Map> getAllElement(@Param("instanceId") Long instanceId);

}