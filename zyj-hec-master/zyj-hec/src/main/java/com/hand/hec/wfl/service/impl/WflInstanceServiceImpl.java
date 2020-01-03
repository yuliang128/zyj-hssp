package com.hand.hec.wfl.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.wfl.mapper.WflInstanceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hand.hec.wfl.dto.WflInstance;
import com.hand.hec.wfl.service.IWflInstanceService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional(rollbackFor = Exception.class)
public class WflInstanceServiceImpl extends BaseServiceImpl<WflInstance> implements IWflInstanceService{
    @Autowired
    WflInstanceMapper wflInstanceMapper;

    @Override
    protected boolean useSelectiveUpdate() {
        return false;
    }

    @Override
    public List<Map> getArrivedElement(Long instanceId) {
        return wflInstanceMapper.getArrivedElement(instanceId);
    }

    @Override
    public List<Map> getAllElement(Long instanceId) {
        return wflInstanceMapper.getAllElement(instanceId);
    }
}