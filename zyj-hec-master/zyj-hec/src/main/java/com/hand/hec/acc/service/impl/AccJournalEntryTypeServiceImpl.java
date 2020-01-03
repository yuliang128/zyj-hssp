package com.hand.hec.acc.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.hand.hec.acc.dto.AccJournalEntryType;
import com.hand.hec.acc.service.IAccJournalEntryTypeService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class AccJournalEntryTypeServiceImpl extends BaseServiceImpl<AccJournalEntryType> implements IAccJournalEntryTypeService{

}