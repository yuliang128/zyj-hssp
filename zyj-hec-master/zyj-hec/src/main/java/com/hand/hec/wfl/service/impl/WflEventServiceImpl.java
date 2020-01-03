package com.hand.hec.wfl.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.wfl.mapper.WflEventMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hand.hec.wfl.dto.WflEvent;
import com.hand.hec.wfl.service.IWflEventService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class WflEventServiceImpl extends BaseServiceImpl<WflEvent> implements IWflEventService {
    @Autowired
    WflEventMapper wflEventMapper;

    @Override
    protected boolean useSelectiveUpdate() {
        return false;
    }


    @Override
    public List<WflEvent> processEventQuery(Long processId, IRequest request){
        return wflEventMapper.processEventQuery(processId,request);
    }
}