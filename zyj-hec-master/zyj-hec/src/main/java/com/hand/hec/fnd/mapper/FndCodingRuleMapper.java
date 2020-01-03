package com.hand.hec.fnd.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.fnd.dto.FndCodingRule;

import java.util.List;

public interface FndCodingRuleMapper extends Mapper<FndCodingRule>{

    /**
     *
     * @param dto
     * @return 返回合适的编码规则
     * @author zhongyu 2019-2-22 16:36
     */
    List<FndCodingRule> queryByCodingRuleObjectId(FndCodingRule dto);

}