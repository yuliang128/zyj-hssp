package com.hand.hec.bpm.mapper;

import com.hand.hec.bpm.dto.TpltLayoutTab;
import com.hand.hap.mybatis.common.Mapper;

import java.util.List;

public interface TpltLayoutTabMapper extends Mapper<TpltLayoutTab>{
    List<TpltLayoutTab> queryByParas(TpltLayoutTab bpmTpltLayoutTab);
}