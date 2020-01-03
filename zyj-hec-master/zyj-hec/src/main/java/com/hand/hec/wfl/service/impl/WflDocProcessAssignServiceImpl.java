package com.hand.hec.wfl.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.wfl.mapper.WflDocProcessAssignMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hand.hec.wfl.dto.WflDocProcessAssign;
import com.hand.hec.wfl.service.IWflDocProcessAssignService;
import org.springframework.transaction.annotation.Transactional;
import uncertain.composite.CompositeMap;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class WflDocProcessAssignServiceImpl extends BaseServiceImpl<WflDocProcessAssign> implements IWflDocProcessAssignService{

    @Autowired
    WflDocProcessAssignMapper wflDocProcessAssignMapper;

    @Override
    protected boolean useSelectiveUpdate() {
        return false;
    }

    @Override
    public List<WflDocProcessAssign> queryWflDocProcessAssign(IRequest context, WflDocProcessAssign wflDocProcessAssign) {
        return wflDocProcessAssignMapper.queryWflDocProcessAssign(wflDocProcessAssign);
    }
}