package com.hand.hec.wfl.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.wfl.dto.WflDocBizRuleAssign;
import com.hand.hec.wfl.dto.WflDocProcessAssign;

import java.util.List;

public interface WflDocBizRuleAssignMapper extends Mapper<WflDocBizRuleAssign>{

    public List<WflDocBizRuleAssign> queryDocBizRuleAssign(WflDocProcessAssign docProcess);

    List<WflDocBizRuleAssign> queryWflDocBizRuleAssign(WflDocBizRuleAssign wflDocBizRuleAssign);
}