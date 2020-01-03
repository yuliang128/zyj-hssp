package com.hand.hec.evt.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.hand.hec.evt.dto.EvtEventLog;
import com.hand.hec.evt.service.IEvtEventLogService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class EvtEventLogServiceImpl extends BaseServiceImpl<EvtEventLog> implements IEvtEventLogService{

}