package com.hand.hec.wfl.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.wfl.dto.WflTask;
import org.apache.ibatis.annotations.Param;

import java.util.*;


public interface WflTaskMapper extends Mapper<WflTask>{
    public List<WflTask> taskNameLov(@Param("processId") Long processId);

    List<WflTask> queryWflTask(WflTask wflTask);
    List<WflTask> queryTaskNameLov(WflTask wflTask);
    List<WflTask> queryCodeAndNameById(@Param("taskId") Long taskId);

    List<WflTask> queryLinkedTask(@Param("taskId") Long taskId);
    List<WflTask> queryByProcessId(@Param("processId") Long processId);

}