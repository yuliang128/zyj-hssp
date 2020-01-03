package com.hand.hec.bgt.service.impl;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.core.interceptor.SecurityTokenInterceptor;
import com.hand.hap.security.TokenUtils;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.bgt.dto.BgtBalanceQueryGroup;
import com.hand.hec.bgt.mapper.BgtBalanceQueryResultMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hand.hec.bgt.dto.BgtBalanceQueryResult;
import com.hand.hec.bgt.service.IBgtBalanceQueryResultService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class BgtBalanceQueryResultServiceImpl extends BaseServiceImpl<BgtBalanceQueryResult>
                implements IBgtBalanceQueryResultService {

    @Autowired
    BgtBalanceQueryResultMapper mapper;

    @Override
    public void groupResult(IRequest request, BgtBalanceQueryGroup group, String groupField) {
        mapper.groupResult(group, groupField);
    }

    @Override
    public Long getMinPeriodNum(IRequest request) {
        return mapper.getMinPeriodNum();
    }

    @Override
    public Long getMaxPeriodNum(IRequest request) {
        return mapper.getMaxPeriodNum();
    }

    @Override
    public void periodSummary(IRequest request, BgtBalanceQueryGroup group, String periodDesc) {
        mapper.periodSummary(group, periodDesc);
        mapper.deleteBySessionId(periodDesc);
    }

    @Override
    public List<BgtBalanceQueryResult> getBudgetBalanceResult(IRequest request, BgtBalanceQueryResult balanceQueryResult, int page, int pageSize) {
        PageHelper.startPage(page,pageSize);
        List<BgtBalanceQueryResult> list = mapper.getBudgetBalanceResult(balanceQueryResult);
        list.forEach(balanceQueryResult1 -> {
            TokenUtils.generateAndSetToken(SecurityTokenInterceptor.LOCAL_SECURITY_KEY.get(), balanceQueryResult);
        });
        return list;
    }

    @Override
    public void deleteBalanceResult(IRequest request) {
        mapper.deleteBySessionId("");
    }

}
