package com.hand.hec.wfl.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.wfl.dto.WflProcess;


/**
 * \* Created with IntelliJ IDEA.
 * \* User: MouseZhou
 * \* Date: 2018/3/21
 * \* Time: 16:40
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */

public interface IWflDocProcessEngineService extends IBaseService<WflProcess>,ProxySelf<IWflDocProcessEngineService> {

    public WflProcess getTargetWflProcess(IRequest request, String docCategory, String docId);
}
