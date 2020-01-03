package com.hand.hec.wfl.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.wfl.mapper.WflInsGatewayMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hand.hec.wfl.dto.WflInsGateway;
import com.hand.hec.wfl.service.IWflInsGatewayService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class WflInsGatewayServiceImpl extends BaseServiceImpl<WflInsGateway> implements IWflInsGatewayService{
    @Autowired
    WflInsGatewayMapper wflInsGatewayMapper;

    @Override
    protected boolean useSelectiveUpdate() {
        return false;
    }

    @Override
    public WflInsGateway selectInsGatewayById(Long insGatewayId) {
        return wflInsGatewayMapper.selectInsGatewayById(insGatewayId);
    }
}