package com.hand.hec.wfl.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.wfl.dto.WflDeliverBizRuleAssign;

import java.util.List;

public interface WflDeliverBizRuleAssignMapper extends Mapper<WflDeliverBizRuleAssign>{

    List<WflDeliverBizRuleAssign> queryWflDeliverBizRuleAssign(WflDeliverBizRuleAssign wflDeliverBizRuleAssign);
}