package com.hand.hec.bgt.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.bgt.dto.BgtBalanceQueryGroup;
import com.hand.hec.bgt.mapper.BgtBalanceQuerySummaryTempMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hand.hec.bgt.dto.BgtBalanceQuerySummaryTemp;
import com.hand.hec.bgt.service.IBgtBalanceQuerySummaryTempService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class BgtBalanceQuerySummaryTempServiceImpl extends BaseServiceImpl<BgtBalanceQuerySummaryTemp>
                implements IBgtBalanceQuerySummaryTempService {

    @Autowired
    BgtBalanceQuerySummaryTempMapper mapper;

    @Override
    public void generateSummary(IRequest request, BgtBalanceQueryGroup group, String filterField) {
        mapper.generateSummary(group, filterField);
    }

    @Override
    public void cleanSummaryTemp(IRequest request) {
        mapper.cleanSummaryTemp();
    }
}
