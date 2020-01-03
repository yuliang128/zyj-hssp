package com.hand.hec.bgt.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.bgt.dto.BgtBalanceQueryGroup;
import com.hand.hec.bgt.mapper.BgtBalanceQueryDetailTempMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hand.hec.bgt.dto.BgtBalanceQueryDetailTemp;
import com.hand.hec.bgt.service.IBgtBalanceQueryDetailTempService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class BgtBalanceQueryDetailTempServiceImpl extends BaseServiceImpl<BgtBalanceQueryDetailTemp>
                implements IBgtBalanceQueryDetailTempService {

    @Autowired
    BgtBalanceQueryDetailTempMapper detailTempMapper;

    @Override
    public void generateBalanceDetail(IRequest request, BgtBalanceQueryGroup group, String filterField,
                    String periodStrategy) {
        detailTempMapper.generateBalanceDetail(group, filterField, periodStrategy);
    }

    @Override
    public void generateReserveDetail(IRequest request, BgtBalanceQueryGroup group, String filterField,
                    String periodStrategy) {
        detailTempMapper.generateReserveDetail(group, filterField, periodStrategy);
    }

    @Override
    public void cleanDetailTemp(IRequest request) {
        detailTempMapper.cleanDetailTemp();
    }
}
