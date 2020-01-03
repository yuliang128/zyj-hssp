package com.hand.hec.wfl.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.wfl.dto.WflVerTaskReceiver;
import com.hand.hec.wfl.service.IWflVerTaskReceiverService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * description
 *
 * @author mouse 2019/03/01 17:51
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class WflVerTaskReceiverServiceImpl extends BaseServiceImpl<WflVerTaskReceiver>
                implements IWflVerTaskReceiverService {

    @Override
    protected boolean useSelectiveUpdate() {
        return false;
    }

}
