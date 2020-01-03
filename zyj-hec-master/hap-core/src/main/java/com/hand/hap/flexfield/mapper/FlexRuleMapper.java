package com.hand.hap.flexfield.mapper;

import java.util.List;

import com.hand.hap.flexfield.dto.FlexRule;
import com.hand.hap.mybatis.common.Mapper;

public interface FlexRuleMapper extends Mapper<FlexRule> {

    /** 匹配规则
     * @param ruleSetCode
     * @return
     */
    List<FlexRule> matchingRule(String ruleSetCode);

}