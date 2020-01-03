package com.hand.hap.flexfield.mapper;

import java.util.List;

import com.hand.hap.flexfield.dto.FlexRuleSet;
import com.hand.hap.mybatis.common.Mapper;

public interface FlexRuleSetMapper extends Mapper<FlexRuleSet> {


    /** 查询规则集
     * @param model
     * @return
     */
    List<FlexRuleSet> queryFlexRuleSet(FlexRuleSet model);

}