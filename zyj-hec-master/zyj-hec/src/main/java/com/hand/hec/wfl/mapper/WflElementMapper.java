package com.hand.hec.wfl.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.wfl.dto.WflElement;

import java.util.List;

public interface WflElementMapper extends Mapper<WflElement> {
    List<WflElement> processElementQuery(WflElement record);
}
