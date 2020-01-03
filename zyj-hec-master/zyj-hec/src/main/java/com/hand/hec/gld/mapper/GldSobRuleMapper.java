package com.hand.hec.gld.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.gld.dto.GldSobRule;
import org.apache.ibatis.annotations.Param;

public interface GldSobRuleMapper extends Mapper<GldSobRule>{
    /**
     *@Description 根据会计规则组+科目获取会计规则明细Id
     *@Author hui.zhao01@hand-china.com
     *@Date 2019/2/21 16:04
     *@Param ruleGroupId 会计规则组Id
     *@Param accountId 科目Id
     *@Version 1.0
     **/
    Long getRuleId(@Param("ruleGroupId") Long ruleGroupId,@Param("accountId") Long accountId);
}