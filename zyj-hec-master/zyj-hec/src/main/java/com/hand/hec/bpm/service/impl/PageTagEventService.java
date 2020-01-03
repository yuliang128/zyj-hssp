package com.hand.hec.bpm.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.bpm.dto.PageTagDataGuide;
import com.hand.hec.bpm.dto.PageTagEvent;
import com.hand.hec.bpm.service.IPageTagDataGuideService;
import com.hand.hec.bpm.service.IPageTagEventService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class PageTagEventService extends BaseServiceImpl<PageTagEvent> implements IPageTagEventService {
}
