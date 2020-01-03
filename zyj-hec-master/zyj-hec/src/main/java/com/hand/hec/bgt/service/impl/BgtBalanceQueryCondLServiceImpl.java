package com.hand.hec.bgt.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.bgt.dto.BgtBalanceQueryCondL;
import com.hand.hec.bgt.mapper.BgtBalanceQueryCondHMapper;
import com.hand.hec.bgt.mapper.BgtBalanceQueryCondLMapper;
import com.hand.hec.bgt.service.IBgtBalanceQueryCondLService;

/**
 * <p>
 * 预算余额查询方案行serviceImpl
 * </p>
 *
 * @author YHL 2019/03/13 18:22
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class BgtBalanceQueryCondLServiceImpl extends BaseServiceImpl<BgtBalanceQueryCondL>
        implements IBgtBalanceQueryCondLService {

    @Autowired
    private BgtBalanceQueryCondLMapper mapper;

    @Autowired
    private BgtBalanceQueryCondHMapper condHMapper;

    @Override
    public List<Map<String, Object>> getControlRuleRange(IRequest request) {
        return mapper.getControlRuleRange();
    }

    @Override
    public List<BgtBalanceQueryCondL> getBalanceQueryCondLBgt(IRequest request, Long balanceQueryCondHId,
            Long bgtOrgId) {
        return mapper.getBalanceQueryCondLBgt(balanceQueryCondHId, bgtOrgId);
    }

    @Override
    public List<BgtBalanceQueryCondL> getBalanceQueryCondLOrg(IRequest request, Long balanceQueryCondHId) {
        return mapper.getBalanceQueryCondLOrg(balanceQueryCondHId);
    }

    @Override
    public List<BgtBalanceQueryCondL> getBalanceQueryCondLDim(IRequest request, Long balanceQueryCondHId) {
        return mapper.getBalanceQueryCondLDim(balanceQueryCondHId);
    }

    @Override
    public void insertBalanceQueryCondL(IRequest request, String balanceQueryConditionCode, String sessionId,
            String parameter, String controlRuleRange, String parameterCode, String parameterLowerLimit,
            String parameterUpperLimit) {
        BgtBalanceQueryCondL bgtBalanceQueryCondL = new BgtBalanceQueryCondL();
        bgtBalanceQueryCondL.setBalanceQueryCondHId(condHMapper.getCondHIdByConditionCode(balanceQueryConditionCode));
        bgtBalanceQueryCondL.setSessionId(sessionId);
        bgtBalanceQueryCondL.setParameter(parameter);
        bgtBalanceQueryCondL.setControlRuleRange(controlRuleRange);
        bgtBalanceQueryCondL.setParameterCode(parameterCode);
        bgtBalanceQueryCondL.setParameterLowerLimit(parameterLowerLimit);
        bgtBalanceQueryCondL.setParameterUpperLimit(parameterUpperLimit);
        self().insertSelective(request, bgtBalanceQueryCondL);
    }

    @Override
    public List<BgtBalanceQueryCondL> getBalanceQueryCondLByCondHId(Long balanceQueryCondHId) {
        return mapper.getBalanceQueryCondLByCondHId(balanceQueryCondHId);
    }

}