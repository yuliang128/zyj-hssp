package com.hand.hec.bgt.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.bgt.dto.BgtBalanceQueryGroup;
import com.hand.hec.bgt.mapper.BgtBudgetReserveInitMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hand.hec.bgt.dto.BgtBudgetReserveInit;
import com.hand.hec.bgt.service.IBgtBudgetReserveInitService;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class BgtBudgetReserveInitServiceImpl extends BaseServiceImpl<BgtBudgetReserveInit>
                implements IBgtBudgetReserveInitService {

    @Autowired
    BgtBudgetReserveInitMapper mapper;

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void initReserveData(IRequest request, BgtBalanceQueryGroup group) {
        mapper.initReserveData(group);
    }

    @Override
    public void cleanReserveData(IRequest request) {
        mapper.cleanReserveData();
    }
}
