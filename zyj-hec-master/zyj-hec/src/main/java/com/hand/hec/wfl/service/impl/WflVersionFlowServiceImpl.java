package com.hand.hec.wfl.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.wfl.dto.WflVersionFlow;
import com.hand.hec.wfl.service.IWflVersionFlowService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * description
 *
 * @author mouse 2019/03/01 20:05
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class WflVersionFlowServiceImpl extends BaseServiceImpl<WflVersionFlow> implements IWflVersionFlowService {

    @Override
    protected boolean useSelectiveUpdate() {
        return false;
    }

}
