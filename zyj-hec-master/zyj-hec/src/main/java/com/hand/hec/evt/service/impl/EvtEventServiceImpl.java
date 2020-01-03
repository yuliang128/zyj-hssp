package com.hand.hec.evt.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.hand.hec.evt.dto.EvtEvent;
import com.hand.hec.evt.service.IEvtEventService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class EvtEventServiceImpl extends BaseServiceImpl<EvtEvent> implements IEvtEventService{

}