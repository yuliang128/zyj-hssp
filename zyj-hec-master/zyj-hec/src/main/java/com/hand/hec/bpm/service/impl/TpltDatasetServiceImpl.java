package com.hand.hec.bpm.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.bpm.dto.TpltDataset;
import com.hand.hec.bpm.mapper.TpltDatasetMapper;
import com.hand.hec.bpm.service.ITpltDatasetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class TpltDatasetServiceImpl extends BaseServiceImpl<TpltDataset> implements ITpltDatasetService {

    @Autowired
    TpltDatasetMapper bpmTpltDatasetsMapper;

    @Override
    public List<TpltDataset> queryByTemplateId(IRequest request, TpltDataset bpmTpltDatasets){
        return bpmTpltDatasetsMapper.queryByTemplateId(bpmTpltDatasets);
    }
}