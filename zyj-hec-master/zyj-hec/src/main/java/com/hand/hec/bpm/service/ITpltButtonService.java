package com.hand.hec.bpm.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.bpm.dto.TpltButton;

import java.util.List;


public interface ITpltButtonService extends IBaseService<TpltButton>, ProxySelf<ITpltButtonService> {

    List<TpltButton> queryByTemplateId(IRequest request, TpltButton bpmTpltButtons);

    void batchSaves(IRequest request, List<TpltButton> bpmTpltButtonsList);

    TpltButton insertTpltButtons(IRequest request, TpltButton bpmTpltButtons);

    TpltButton updateTpltButtons(IRequest request, TpltButton bpmTpltButtons);

    TpltButton deleteTpltButtons(IRequest request, TpltButton bpmTpltButtons);

}