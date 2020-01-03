package com.hand.hec.gld.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.gld.dto.GldSobRuleSegment;
import org.apache.ibatis.annotations.Param;

public interface GldSobRuleSegmentMapper extends Mapper<GldSobRuleSegment>{
    /**
     * 获取会计规则明细
     *@Author hui.zhao01@hand-china.com
     *@Date 2019/2/20 20:09
     *@param  ruleId 规则Id
     *@param segmentId 段值Id
     *@return GldSobRuleSegment
     *@Version 1.0
     **/
    GldSobRuleSegment getRuleSegmentInfo(@Param("ruleId")Long ruleId,@Param("segmentId")Long segmentId);
}