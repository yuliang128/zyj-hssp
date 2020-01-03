package com.hand.hec.evt.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.hand.hec.evt.dto.EvtEventHandleRecord;
import com.hand.hec.evt.service.IEvtEventHandleRecordService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class EvtEventHandleRecordServiceImpl extends BaseServiceImpl<EvtEventHandleRecord> implements IEvtEventHandleRecordService{

}