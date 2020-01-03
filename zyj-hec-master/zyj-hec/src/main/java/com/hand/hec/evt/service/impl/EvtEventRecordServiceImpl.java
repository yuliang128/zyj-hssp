package com.hand.hec.evt.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.hand.hec.evt.dto.EvtEventRecord;
import com.hand.hec.evt.service.IEvtEventRecordService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class EvtEventRecordServiceImpl extends BaseServiceImpl<EvtEventRecord> implements IEvtEventRecordService{

}