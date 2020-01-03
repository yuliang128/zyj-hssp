package com.hand.hec.bpm.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.bpm.dto.TpltLayout;
import com.hand.hec.bpm.dto.TpltLayoutBasic;

import java.util.List;


public interface ITpltLayoutBasicService extends IBaseService<TpltLayoutBasic>, ProxySelf<ITpltLayoutBasicService> {
    List<TpltLayout> queryByTemplateId(IRequest request, TpltLayout layout);

    TpltLayout insertBasic(IRequest request, TpltLayout layout);

    TpltLayout updateBasic(IRequest request, TpltLayout layout);

    TpltLayout deleteBasic(IRequest request, TpltLayout layout);

    List<TpltLayout> batchUpdateLayout(IRequest request, List<TpltLayout> layoutList);
}