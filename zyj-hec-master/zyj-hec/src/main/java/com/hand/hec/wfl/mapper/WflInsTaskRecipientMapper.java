package com.hand.hec.wfl.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.wfl.dto.WflInsTaskRecipient;

public interface WflInsTaskRecipientMapper extends Mapper<WflInsTaskRecipient>{
    Long getRecipientApproveCount(WflInsTaskRecipient recipient);
}