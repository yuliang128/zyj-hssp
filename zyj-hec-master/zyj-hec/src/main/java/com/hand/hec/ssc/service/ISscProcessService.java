package com.hand.hec.ssc.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.ssc.dto.SscProcess;

import java.util.List;

public interface ISscProcessService extends IBaseService<SscProcess>, ProxySelf<ISscProcessService>{
    /**
     *获取流程信息
     *
     *@Author hui.zhao01@hand-china.com
     *@Date 2019/4/2 14:11
     *@param requestContext
     *@param dispatchRecordId
     *@param taskId
     *@param page
     *@param pageSize
     *@return List<SscProcess>
     *@Version 1.0
     **/
    List<SscProcess> processQuery(IRequest requestContext, Long dispatchRecordId, Long taskId, int page, int pageSize);
}