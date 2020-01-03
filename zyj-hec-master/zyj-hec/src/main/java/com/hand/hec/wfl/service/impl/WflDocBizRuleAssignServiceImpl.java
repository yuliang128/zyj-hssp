package com.hand.hec.wfl.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.wfl.mapper.WflDocBizRuleAssignMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hand.hec.wfl.dto.WflDocBizRuleAssign;
import com.hand.hec.wfl.service.IWflDocBizRuleAssignService;
import org.springframework.transaction.annotation.Transactional;
import uncertain.composite.CompositeMap;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class WflDocBizRuleAssignServiceImpl extends BaseServiceImpl<WflDocBizRuleAssign> implements IWflDocBizRuleAssignService {

    @Autowired
    WflDocBizRuleAssignMapper wflDocBizRuleAssignMapper;

    @Override
    protected boolean useSelectiveUpdate() {
        return false;
    }


    @Override
    public List<WflDocBizRuleAssign> queryWflDocBizRuleAssign(IRequest context, WflDocBizRuleAssign wflDocBizRuleAssign) {
        return wflDocBizRuleAssignMapper.queryWflDocBizRuleAssign(wflDocBizRuleAssign);
    }
}