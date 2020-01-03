package com.hand.hec.wfl.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.wfl.dto.WflProcess;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface WflProcessMapper extends Mapper<WflProcess> {

    List<Map> queryAllElement(@Param("processId") Long processId);

    List<WflProcess> queryWflProcess(WflProcess wflProcess);

    List<WflProcess> queryProcessNameLov(WflProcess wflProcess);

    List<WflProcess> queryProcessCodeAndName(@Param("processId") Long processId);

    void changeConfigType(@Param("processId") Long processId, @Param("configType") String configType);
}
