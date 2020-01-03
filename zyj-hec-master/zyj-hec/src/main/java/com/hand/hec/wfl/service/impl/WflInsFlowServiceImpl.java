package com.hand.hec.wfl.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.wfl.mapper.WflInsFlowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hand.hec.wfl.dto.WflInsFlow;
import com.hand.hec.wfl.service.IWflInsFlowService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class WflInsFlowServiceImpl extends BaseServiceImpl<WflInsFlow> implements IWflInsFlowService{
    @Autowired
    WflInsFlowMapper wflInsFlowMapper;

    @Override
    protected boolean useSelectiveUpdate() {
        return false;
    }

    @Override
    public List<WflInsFlow> getNextFlow(String fromElementType, Long fromElementId) {
        return wflInsFlowMapper.getNextFlow(fromElementType, fromElementId);
    }

    @Override
    public List<WflInsFlow> getPreFlow(String toElementType, Long toElementId) {
        return wflInsFlowMapper.getPreFlow(toElementType, toElementId);
    }
}