package com.hand.hec.wfl.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.wfl.mapper.WflFlowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hand.hec.wfl.dto.WflFlow;
import com.hand.hec.wfl.service.IWflFlowService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class WflFlowServiceImpl extends BaseServiceImpl<WflFlow> implements IWflFlowService {
    @Autowired
    WflFlowMapper wflFlowMapper;

    @Override
    protected boolean useSelectiveUpdate() {
        return false;
    }


    @Override
    public List<WflFlow> processFlowQuery(Long processId, IRequest request){
        return wflFlowMapper.processFlowQuery(processId, request);
    }
}