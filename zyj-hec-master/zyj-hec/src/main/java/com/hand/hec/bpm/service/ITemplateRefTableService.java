package com.hand.hec.bpm.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.bpm.dto.TemplateRefTable;

import java.util.List;


public interface ITemplateRefTableService extends IBaseService<TemplateRefTable>, ProxySelf<ITemplateRefTableService> {
    List<TemplateRefTable> queryByTemplateId(IRequest request, TemplateRefTable TemplateRefTable);
}