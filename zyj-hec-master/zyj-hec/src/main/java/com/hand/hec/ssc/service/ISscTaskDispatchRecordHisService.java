package com.hand.hec.ssc.service;

import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.ssc.dto.SscTaskDispatchRecordHis;

import java.util.List;

public interface ISscTaskDispatchRecordHisService extends IBaseService<SscTaskDispatchRecordHis>, ProxySelf<ISscTaskDispatchRecordHisService>{
    /**
     *获取历史派工记录
     *
     *@Author hui.zhao01@hand-china.com
     *@Date 2019/3/26 18:39
     *@param taskId 任务Id
     *@param status 状态
     *@param processTypeCode 操作类型代码
     *@return List<SscTaskDispatchRecordHis>
     *@Version 1.0
     **/
    List<SscTaskDispatchRecordHis> getAllDispatchRecordHis(Long taskId,String status,String processTypeCode);
}