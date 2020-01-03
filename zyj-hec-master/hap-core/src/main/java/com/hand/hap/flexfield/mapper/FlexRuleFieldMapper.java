package com.hand.hap.flexfield.mapper;

import java.util.List;

import com.hand.hap.flexfield.dto.FlexRuleField;
import com.hand.hap.mybatis.common.Mapper;

public interface FlexRuleFieldMapper extends Mapper<FlexRuleField> {

    /** 根据 rule查询对应ruleField
     * @param flexRuleField
     * @return
     */
    List<FlexRuleField> queryFlexField(FlexRuleField flexRuleField);

}