package com.hand.hec.gld.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.gld.dto.GldSobRuleGroup;
import org.apache.ibatis.annotations.Param;

public interface GldSobRuleGroupMapper extends Mapper<GldSobRuleGroup>{
    /**
     *@Description 获取会计规则组ID
     *@Author hui.zhao01@hand-china.com
     *@Date 2019/2/21 16:00
     *@Param setOfBooksId 账套Id
     *@Param accEntityId 核算主体Id
     *@Version 1.0
     **/
    Long getRuleGroupId(@Param("setOfBooksId") Long setOfBooksId,@Param("accEntityId") Long accEntityId);
}