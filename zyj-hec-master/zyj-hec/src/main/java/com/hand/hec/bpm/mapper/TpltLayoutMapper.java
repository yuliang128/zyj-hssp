package com.hand.hec.bpm.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.bpm.dto.TpltLayout;

import java.util.List;

public interface TpltLayoutMapper extends Mapper<TpltLayout> {
    List<TpltLayout> queryByTemplateId(TpltLayout TpltLayout);
}