package com.hand.hec.bgt.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.bgt.mapper.BgtBalanceQueryIdTempMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hand.hec.bgt.dto.BgtBalanceQueryIdTemp;
import com.hand.hec.bgt.service.IBgtBalanceQueryIdTempService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class BgtBalanceQueryIdTempServiceImpl extends BaseServiceImpl<BgtBalanceQueryIdTemp> implements IBgtBalanceQueryIdTempService{

    @Autowired
    BgtBalanceQueryIdTempMapper mapper;

    @Override
    public void cleanIdTemp(IRequest request) {
        mapper.cleanIdTemp();
    }
}