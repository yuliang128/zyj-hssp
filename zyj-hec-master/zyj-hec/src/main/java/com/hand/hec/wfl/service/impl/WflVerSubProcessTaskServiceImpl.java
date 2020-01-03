package com.hand.hec.wfl.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.wfl.dto.WflVerSubProcessTask;
import com.hand.hec.wfl.service.IWflVerSubProcessTaskService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * description
 *
 * @author mouse 2019/03/01 17:28
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class WflVerSubProcessTaskServiceImpl extends BaseServiceImpl<WflVerSubProcessTask> implements IWflVerSubProcessTaskService {

    @Override
    protected boolean useSelectiveUpdate() {
        return false;
    }

}
