package com.hand.hec.wfl.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.fnd.dto.FndBusinessRule;
import com.hand.hec.fnd.service.IFndBusinessRuleService;
import com.hand.hec.fnd.service.IFndBusinessRuleEngineService;
import com.hand.hec.wfl.dto.WflDeliverBizRuleAssign;
import com.hand.hec.wfl.dto.WflInsTaskHierarchy;
import com.hand.hec.wfl.dto.WflInstance;
import com.hand.hec.wfl.mapper.WflDeliverMapper;
import com.hand.hec.wfl.service.IWflDeliverBizRuleAssignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hand.hec.wfl.dto.WflDeliver;
import com.hand.hec.wfl.service.IWflDeliverService;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class WflDeliverServiceImpl extends BaseServiceImpl<WflDeliver> implements IWflDeliverService {
    @Autowired
    WflDeliverMapper wflDeliverMapper;

    @Autowired
    IWflDeliverBizRuleAssignService wflDeliverBizRuleAssignService;

    @Autowired
    IFndBusinessRuleService fndBusinessRuleService;

    @Autowired
    IFndBusinessRuleEngineService fndBusinessRuleEngineService;

    @Override
    public List<WflDeliver> selectWflDeliver(IRequest context, WflDeliver wflDeliver) {
        return wflDeliverMapper.selectWflDeliver(wflDeliver);
    }

    @Override
    public List<WflDeliver> selectWflDeliverLov(IRequest context, WflDeliver wflDeliver) {
        return wflDeliverMapper.selectWflDeliverLov(wflDeliver);
    }

    @Override
    protected boolean useSelectiveUpdate() {
        return false;
    }

    @Override
    public List<WflDeliver> getMatchedDeliver(IRequest context, WflInstance instance, WflInsTaskHierarchy insTaskHierarchy) {
        List<WflDeliver> resultDeliverList = new ArrayList<WflDeliver>();
        //根据当前待审批接收者
        WflDeliver deliver = new WflDeliver();
        deliver.setDocCategory(instance.getDocCategory());
        deliver.setProcessId(instance.getProcessId());
        deliver.setFromUserId(insTaskHierarchy.getApproverId());
        //有效性的判断
        List<WflDeliver> deliverList = wflDeliverMapper.getMatchedDeliver(deliver);

        for (WflDeliver currentDeliver : deliverList) {
            //查找当前转交者的权限规则设置
            WflDeliverBizRuleAssign deliverBizRuleAssign = new WflDeliverBizRuleAssign();
            deliverBizRuleAssign.setDeliverId(currentDeliver.getDeliverId());
            deliverBizRuleAssign.setEnabledFlag("Y");
            List<WflDeliverBizRuleAssign> deliverBizRuleAssignList = wflDeliverBizRuleAssignService.selectOptions(context, deliverBizRuleAssign,new Criteria(deliverBizRuleAssign));
            //如果权限规则分配数量为0，则认为当前转交有效
            if (deliverBizRuleAssignList.size() == 0) {
                resultDeliverList.add(currentDeliver);
                continue;
            } else {
                for (WflDeliverBizRuleAssign currentDeliverBizRuleAssign : deliverBizRuleAssignList) {
                    //循环当前权限规则分配，校验是否有通过的权限规则
                    FndBusinessRule businessRule = new FndBusinessRule();
                    businessRule.setBusinessRuleId(currentDeliverBizRuleAssign.getBusinessRuleId());
                    businessRule = fndBusinessRuleService.selectByPrimaryKey(context, businessRule);
                    boolean validateResult = fndBusinessRuleEngineService.validateBusinessRule(context, businessRule, instance.getDocCategory(), String.valueOf(instance.getDocId()));
                    if (validateResult) {
                        resultDeliverList.add(currentDeliver);
                        continue;
                    }
                }
            }
        }

        return resultDeliverList;
    }
}