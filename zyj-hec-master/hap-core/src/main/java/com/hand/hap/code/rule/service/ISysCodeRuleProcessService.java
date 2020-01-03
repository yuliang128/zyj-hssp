package com.hand.hap.code.rule.service;

import java.util.Map;

import com.hand.hap.code.rule.exception.CodeRuleException;

/**
 * @author xiangyu.qi@hand-china.com on 2017/8/23.
 */

public interface ISysCodeRuleProcessService {

    String getRuleCode(String ruleCode) throws CodeRuleException;


    String getRuleCode(String ruleCode ,Map<String,String> variables) throws CodeRuleException;
}
