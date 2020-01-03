package com.hand.hec.ssc.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.ssc.dto.SscWorkRecord;

import java.util.Date;
import java.util.List;

public interface SscWorkRecordMapper extends Mapper<SscWorkRecord>{
    /**
     *获取员工工作状态
     *
     *@Author hui.zhao01@hand-china.com
     *@Date 2019/4/1 14:52
     *@param currentDate 当前日期
     *@return
     *@Version 1.0
     **/
    List<SscWorkRecord> getWorkStatus(Date currentDate);
}