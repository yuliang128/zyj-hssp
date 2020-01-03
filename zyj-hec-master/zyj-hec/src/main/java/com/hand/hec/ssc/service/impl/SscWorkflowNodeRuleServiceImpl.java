package com.hand.hec.ssc.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.util.DateUtils;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.fnd.dto.FndBusinessRule;
import com.hand.hec.fnd.service.IFndBusinessRuleEngineService;
import com.hand.hec.fnd.service.IFndBusinessRuleService;
import com.hand.hec.ssc.dto.SscTaskPool;
import com.hand.hec.ssc.dto.SscWorkflowNodeRule;
import com.hand.hec.ssc.mapper.SscWorkflowNodeRuleMapper;
import com.hand.hec.ssc.service.ISscWorkflowNodeRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class SscWorkflowNodeRuleServiceImpl extends BaseServiceImpl<SscWorkflowNodeRule>
                implements ISscWorkflowNodeRuleService {
    @Autowired
    private SscWorkflowNodeRuleMapper sscWorkflowNodeRuleMapper;

    @Autowired
    private IFndBusinessRuleEngineService fndBusinessRuleEngineService;

    @Autowired
    private IFndBusinessRuleService fndBusinessRuleService;

    @Override
    public boolean validateBusinessRule(IRequest iRequest, SscTaskPool sscTaskPool, Long nodeId) {
        int count = 0;
        boolean flag;
        List<SscWorkflowNodeRule> sscWorkflowNodeRules = new ArrayList<>();
        sscWorkflowNodeRules = sscWorkflowNodeRuleMapper.getAllRules(sscTaskPool.getDocWflAssignId(), nodeId,
                        DateUtils.getCurrentDate());
        if (!sscWorkflowNodeRules.isEmpty() && sscWorkflowNodeRules != null) {
            for (SscWorkflowNodeRule sscWorkflowNodeRule : sscWorkflowNodeRules) {
                FndBusinessRule fndBusinessRule = new FndBusinessRule();
                fndBusinessRule.setBusinessRuleId(sscWorkflowNodeRule.getWflBusinessRuleId());
                fndBusinessRule = fndBusinessRuleService.selectByPrimaryKey(iRequest, fndBusinessRule);
                flag = fndBusinessRuleEngineService.validateBusinessRule(iRequest, fndBusinessRule,
                                sscTaskPool.getDocCategory(), sscTaskPool.getDocId().toString());
                if(flag){
                    return true;
                }
            }
        }
        if(count>0){
            return false;
        }
        return true;
    }

}
