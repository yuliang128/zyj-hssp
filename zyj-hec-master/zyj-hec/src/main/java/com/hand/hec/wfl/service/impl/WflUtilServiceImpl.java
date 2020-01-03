package com.hand.hec.wfl.service.impl;

import com.hand.hec.wfl.cache.WflVersionProcessCache;
import com.hand.hec.wfl.cache.WflVersionUtil;
import com.hand.hec.wfl.dto.WflInstance;
import com.hand.hec.wfl.dto.WflVersionProcess;
import com.hand.hec.wfl.service.IWflUtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * \* Created with IntelliJ IDEA. \* User: MouseZhou \* Date: 2018/3/23 \* Time: 16:49 \* To change
 * this template use File | Settings | File Templates. \* Description: \
 */

@Service
@Transactional(rollbackFor = Exception.class)
public class WflUtilServiceImpl implements IWflUtilService {

    @Autowired
    ApplicationContext applicationContext;

    @Override
    public String getInstanceDesc(WflInstance instance) {
        return null;
    }

    @Override
    public WflVersionProcess getVersionProcess(WflInstance instance) {
        WflVersionProcessCache cache = (WflVersionProcessCache) applicationContext.getBean("wflVersionProcessCache");
        Long version = instance.getVersion();
        Long processId = instance.getProcessId();
        return (WflVersionProcess) cache.getValue(WflVersionUtil.getVersion(processId, version));
    }
}
