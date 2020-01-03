package com.hand.hec.bpm.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.bpm.dto.TpltTagBasic;

import java.util.List;


public interface ITpltTagBasicService extends IBaseService<TpltTagBasic>, ProxySelf<ITpltTagBasicService> {
    List<TpltTagBasic> queryByLayoutId(IRequest request, TpltTagBasic bpmTpltTagsBasic);

    List<TpltTagBasic> selectTpltTagBasicLov(IRequest request, TpltTagBasic bpmTpltTagsBasic);
}