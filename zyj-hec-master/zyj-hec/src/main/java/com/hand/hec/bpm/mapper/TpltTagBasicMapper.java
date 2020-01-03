package com.hand.hec.bpm.mapper;

import com.hand.hec.bpm.dto.TpltTagBasic;
import com.hand.hap.mybatis.common.Mapper;

import java.util.List;

public interface TpltTagBasicMapper extends Mapper<TpltTagBasic>{
    List<TpltTagBasic> queryByLayoutId(TpltTagBasic bpmTpltTagsBasic);

    List<TpltTagBasic> queryByParas(TpltTagBasic bpmTpltTagsBasic);
    List<TpltTagBasic> selectTpltTagBasicLov(TpltTagBasic bpmTpltTagsBasic);
}