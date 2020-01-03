package com.hand.hec.acc.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.hand.hec.acc.dto.AccRuleJournalEntry;
import com.hand.hec.acc.service.IAccRuleJournalEntryService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class AccRuleJournalEntryServiceImpl extends BaseServiceImpl<AccRuleJournalEntry> implements IAccRuleJournalEntryService{

}