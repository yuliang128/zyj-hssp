package com.hand.hec.ssc.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.ssc.dto.SscTaskDispatchRecordHis;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SscTaskDispatchRecordHisMapper extends Mapper<SscTaskDispatchRecordHis>{
    /**
     *获取历史派工记录
     *
     *@Author hui.zhao01@hand-china.com
     *@Date 2019/3/26 18:39
     *@param taskId 任务Id
     *@param status 状态
     * @param processTypeCode 操作类型代码
     *@return List<SscTaskDispatchRecordHis>
     *@Version 1.0
     **/
    List<SscTaskDispatchRecordHis> getAllDispatchRecordHis(@Param("taskId") Long taskId,@Param("status") String status,@Param("processTypeCode") String processTypeCode);
}