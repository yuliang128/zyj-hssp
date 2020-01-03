package com.hand.hec.ssc.mapper;


import org.apache.ibatis.annotations.Param;

import java.util.List;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.ssc.dto.SscTaskDispatchAdvice;

public interface SscTaskDispatchAdviceMapper extends Mapper<SscTaskDispatchAdvice>{
    /**
     * 获取当前任务可处理人员
     *
     * @param taskId 任务ID
     * @author ngls.luhui 2019-03-26 16:40
     * @return 所有可处理人员的姓名，以逗号分隔
     */
    List<String> getAdviceWorkEmps(@Param("taskId") Long taskId);
}