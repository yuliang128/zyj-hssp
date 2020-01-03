package com.hand.hec.bpm.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.bpm.dto.PageTag;

import java.util.List;


public interface IPageTagService extends IBaseService<PageTag>, ProxySelf<IPageTagService> {


    PageTag insertPageTag(IRequest request, PageTag tag);

    PageTag updatePageTag(IRequest request, PageTag tag);

    int deletePageTag(IRequest request, PageTag tag);

    List<PageTag> batchUpdatePageTag(IRequest request, List<PageTag> tagList);

    List<PageTag> queryPageTag(IRequest request, Long layoutId);

}