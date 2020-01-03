package com.hand.hec.bgt.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.hand.hec.bgt.dto.BgtJournalTypeRefVer;
import com.hand.hec.bgt.service.IBgtJournalTypeRefVerService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class BgtJournalTypeRefVerServiceImpl extends BaseServiceImpl<BgtJournalTypeRefVer> implements IBgtJournalTypeRefVerService{

}