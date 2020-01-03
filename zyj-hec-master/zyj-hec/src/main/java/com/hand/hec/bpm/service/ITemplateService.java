package com.hand.hec.bpm.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.bpm.dto.Template;

import java.util.List;

public interface ITemplateService extends IBaseService<Template>, ProxySelf<ITemplateService> {
    List<Template> selectAllByLike(Template bpmTemplatesDTO);

    Template setTemplateChange(IRequest request, Template bpmTemplatesDTO);

//    void changeBpmPageButtons(IRequest request, Long templateId, Long pageId);
//
//    void changeBpmPageLayoutBasic(IRequest request, Long templateId, Long pageId);

    List<Template> queryTemplateLov(IRequest request, Template bpmTemplates);

    List<Template> queryDatesetFetch(IRequest request, Long pageId);
}
