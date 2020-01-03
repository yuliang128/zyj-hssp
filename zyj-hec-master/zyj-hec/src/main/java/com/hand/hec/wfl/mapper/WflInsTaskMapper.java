package com.hand.hec.wfl.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.wfl.dto.WflInsTask;

public interface WflInsTaskMapper extends Mapper<WflInsTask>{

    Long getPeopleAgreeCount(Long insTaskId);

    Long getPeopleRejectCount(Long insTaskId);

    Long getRuleAgreeCount(Long insTaskId);

    Long getRuleRejectCount(Long insTaskId);

    Long getLineAgreeCount(Long insTaskId);

    Long getLineRejectCount(Long insTaskId);

    Long getAllPeopleCount(Long insTaskId);

    Long getAllRuleCount(Long insTaskId);
}