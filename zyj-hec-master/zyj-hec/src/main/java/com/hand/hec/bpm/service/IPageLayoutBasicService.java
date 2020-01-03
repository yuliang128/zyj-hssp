package com.hand.hec.bpm.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.bpm.dto.PageLayout;
import com.hand.hec.bpm.dto.PageLayoutBasic;

import java.util.List;


public interface IPageLayoutBasicService extends IBaseService<PageLayoutBasic>, ProxySelf<IPageLayoutBasicService> {
    List<PageLayout> queryByPageId(IRequest request, PageLayout layout);

    PageLayout insertBasic(IRequest request, PageLayout layout);

    PageLayout updateBasic(IRequest request, PageLayout layout);

    PageLayout deleteBasic(IRequest request, PageLayout layout);

    List<PageLayout> batchUpdateLayout(IRequest request, List<PageLayout> layoutList);
}