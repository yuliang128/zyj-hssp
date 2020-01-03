package com.hand.hec.wfl.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.hand.hec.wfl.dto.WflUserTask;
import com.hand.hec.wfl.service.IWflUserTaskService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class WflUserTaskServiceImpl extends BaseServiceImpl<WflUserTask> implements IWflUserTaskService {

    @Override
    protected boolean useSelectiveUpdate() {
        return false;
    }

}