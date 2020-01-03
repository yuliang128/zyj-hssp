package com.hand.hec.bpm.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.bpm.dto.Page;

import java.util.Map;


public interface IPageService extends IBaseService<Page>, ProxySelf<IPageService> {

    Page insertPage(IRequest request, Page page);

    int deletePage(IRequest request, Page page);

    public Map queryTemplateReference(IRequest iRequest, Long pageId);

    void copyPage(Long pageId, IRequest iRequest);
}