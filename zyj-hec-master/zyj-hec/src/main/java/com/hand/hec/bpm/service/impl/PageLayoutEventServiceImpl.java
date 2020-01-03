package com.hand.hec.bpm.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.bpm.dto.PageLayoutEvent;
import com.hand.hec.bpm.service.IPageLayoutEventService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class PageLayoutEventServiceImpl extends BaseServiceImpl<PageLayoutEvent> implements IPageLayoutEventService {

}