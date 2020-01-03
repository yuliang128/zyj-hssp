package com.hand.hec.wfl.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.wfl.dto.WflVersionTask;
import com.hand.hec.wfl.service.IWflVersionTaskService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * description
 *
 * @author mouse 2019/03/01 14:48
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class WflVersionTaskServiceImpl extends BaseServiceImpl<WflVersionTask> implements IWflVersionTaskService {

    @Override
    protected boolean useSelectiveUpdate() {
        return false;
    }

}
