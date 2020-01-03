package com.hand.hec.wfl.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.hand.hec.wfl.dto.WflSubProcessTask;
import com.hand.hec.wfl.service.IWflSubProcessTaskService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class WflSubProcessTaskServiceImpl extends BaseServiceImpl<WflSubProcessTask> implements IWflSubProcessTaskService{

    @Override
    protected boolean useSelectiveUpdate() {
        return false;
    }

}