package com.hand.hec.ssc.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.ssc.dto.SscProcess;

import java.util.List;

public interface SscProcessMapper extends Mapper<SscProcess>{
    /**
     *获取流程信息
     *
     *@Author hui.zhao01@hand-china.com
     *@Date 2019/4/2 14:11
     *@param dispatchRecordId
     *@param taskId
     *@return List<SscProcess>
     *@Version 1.0
     **/
    List<SscProcess> processQuery(Long dispatchRecordId, Long taskId);
}