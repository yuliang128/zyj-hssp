package com.hand.hec.bpm.mapper;

import com.hand.hec.bpm.dto.TpltDataset;
import com.hand.hap.mybatis.common.Mapper;

import java.util.List;

public interface TpltDatasetMapper extends Mapper<TpltDataset>{
    List<TpltDataset> queryByTemplateId(TpltDataset bpmTpltDatasets);
}