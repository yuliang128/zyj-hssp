package com.hand.hec.bgt.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.bgt.dto.BgtBalanceQueryGroup;
import com.hand.hec.bgt.mapper.BgtJournalBalanceInitMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hand.hec.bgt.dto.BgtJournalBalanceInit;
import com.hand.hec.bgt.service.IBgtJournalBalanceInitService;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

@Service
public class BgtJournalBalanceInitServiceImpl extends BaseServiceImpl<BgtJournalBalanceInit>
                implements IBgtJournalBalanceInitService {

    @Autowired
    BgtJournalBalanceInitMapper mapper;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,rollbackFor = Exception.class)
    public void initBalanceData(IRequest request, BgtBalanceQueryGroup group) {
        mapper.initBalanceData(group);
    }

    @Override
    public void clearBalanceData(IRequest request) {
        mapper.clearBalanceData();
    }
}
