package com.hand.hec.wfl.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.wfl.dto.WflVersionGateway;
import com.hand.hec.wfl.service.IWflVersionGatewayService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * description
 *
 * @author mouse 2019/03/01 14:54
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class WflVersionGatewayServiceImpl extends BaseServiceImpl<WflVersionGateway> implements IWflVersionGatewayService {

    @Override
    protected boolean useSelectiveUpdate() {
        return false;
    }

}
