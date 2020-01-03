package com.hand.hec.ssc.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.ssc.dto.SscWorkRecord;

public interface ISscWorkRecordService extends IBaseService<SscWorkRecord>, ProxySelf<ISscWorkRecordService>{
    /**
     *开始工作
     *
     *@Author hui.zhao01@hand-china.com
     *@Date 2019/4/1 14:52
     *@param iRequest
     *@return
     *@Version 1.0
     **/
    void startWork(IRequest iRequest);

    /**
     *暂停工作
     *
     *@Author hui.zhao01@hand-china.com
     *@Date 2019/4/1 14:52
     *@param iRequest
     *@return
     *@Version 1.0
     **/
    void pauseWork(IRequest iRequest);

    /**
     *结束工作
     *
     *@Author hui.zhao01@hand-china.com
     *@Date 2019/4/1 14:52
     *@param iRequest
     *@return
     *@Version 1.0
     **/
    void stopWork(IRequest iRequest);

    /**
     *获取员工工作状态
     *
     *@Author hui.zhao01@hand-china.com
     *@Date 2019/4/1 14:52
     *@param iRequest
     *@return
     *@Version 1.0
     **/
    String getWorkStatus(IRequest iRequest);
}