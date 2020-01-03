package com.hand.hec.bpm.controllers;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hec.bpm.dto.TpltDataset;
import com.hand.hec.bpm.service.ITpltDatasetService;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Controller
public class TpltDatasetController extends BaseController {

    @Autowired
    private ITpltDatasetService service;

    public List<TpltDataset> queryByTemplateId(IRequest request, TpltDataset bpmTpltDatasets) {
        return service.queryByTemplateId(request, bpmTpltDatasets);
    }
}