package com.hand.hec.wfl.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.wfl.mapper.WflDeliverBizRuleAssignMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hand.hec.wfl.dto.WflDeliverBizRuleAssign;
import com.hand.hec.wfl.service.IWflDeliverBizRuleAssignService;
import org.springframework.transaction.annotation.Transactional;
import uncertain.composite.CompositeMap;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class WflDeliverBizRuleAssignServiceImpl extends BaseServiceImpl<WflDeliverBizRuleAssign> implements IWflDeliverBizRuleAssignService{

    @Autowired
    WflDeliverBizRuleAssignMapper wflDeliverBizRuleAssignMapper;

    @Override
    protected boolean useSelectiveUpdate() {
        return false;
    }

    @Override
    public List<WflDeliverBizRuleAssign> queryWflDeliverBizRuleAssign(CompositeMap context, WflDeliverBizRuleAssign wflDeliverBizRuleAssign) {
        return wflDeliverBizRuleAssignMapper.queryWflDeliverBizRuleAssign(wflDeliverBizRuleAssign);
    }
}