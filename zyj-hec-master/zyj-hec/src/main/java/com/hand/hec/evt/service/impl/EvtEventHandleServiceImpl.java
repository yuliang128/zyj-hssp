package com.hand.hec.evt.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.hand.hec.evt.dto.EvtEventHandle;
import com.hand.hec.evt.service.IEvtEventHandleService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class EvtEventHandleServiceImpl extends BaseServiceImpl<EvtEventHandle> implements IEvtEventHandleService{

}