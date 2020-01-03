package com.hand.hec.wfl.service.impl;

import com.hand.hap.system.service.IBaseService;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.wfl.dto.WflVersionEvent;
import com.hand.hec.wfl.service.IWflVersionEventService;
import com.hand.hec.wfl.service.IWflVersionProcessService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * description
 *
 * @author mouse 2019/03/01 14:32
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class WflVersionEventServiceImpl extends BaseServiceImpl<WflVersionEvent> implements IWflVersionEventService {

    @Override
    protected boolean useSelectiveUpdate() {
        return false;
    }

}
