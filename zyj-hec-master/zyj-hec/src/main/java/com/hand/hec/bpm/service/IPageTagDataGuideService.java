package com.hand.hec.bpm.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.bpm.dto.PageTagBasic;
import com.hand.hec.bpm.dto.PageTagDataGuide;
import com.hand.hec.bpm.dto.PageTagLabel;

import java.util.List;


public interface IPageTagDataGuideService extends IBaseService<PageTagDataGuide>, ProxySelf<IPageTagDataGuideService> {

    void clearDataGuideEvent(IRequest request, PageTagBasic tag, List<PageTagDataGuide> dataGuideList);

    void generateDataGuideEvent(IRequest request, PageTagBasic tag, List<PageTagDataGuide> dataGuideList);
}