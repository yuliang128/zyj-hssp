package com.hand.hec.wfl.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.wfl.dto.WflVersionUserTask;
import com.hand.hec.wfl.service.IWflVersionUserTaskService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * description
 *
 * @author mouse 2019/03/01 17:05
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class WflVersionUserTaskServiceImpl extends BaseServiceImpl<WflVersionUserTask>
                implements IWflVersionUserTaskService {

    @Override
    protected boolean useSelectiveUpdate() {
        return false;
    }

}
