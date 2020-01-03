package com.hand.hec.bpm.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.bpm.dto.TpltDataset;

import java.util.List;


public interface ITpltDatasetService extends IBaseService<TpltDataset>, ProxySelf<ITpltDatasetService> {
    List<TpltDataset> queryByTemplateId(IRequest request, TpltDataset bpmTpltDatasets);
}