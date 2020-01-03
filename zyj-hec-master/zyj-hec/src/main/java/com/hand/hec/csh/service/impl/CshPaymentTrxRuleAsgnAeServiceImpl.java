package com.hand.hec.csh.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hand.hec.csh.dto.CshPaymentTrxRuleAsgnAe;
import com.hand.hec.csh.mapper.CshPaymentTrxRuleAsgnAeMapper;
import com.hand.hec.csh.service.ICshPaymentTrxRuleAsgnAeService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class CshPaymentTrxRuleAsgnAeServiceImpl extends BaseServiceImpl<CshPaymentTrxRuleAsgnAe> implements ICshPaymentTrxRuleAsgnAeService{

    @Autowired
    CshPaymentTrxRuleAsgnAeMapper cshPaymentTrxRuleAsgnAeMapper;

    @Override
    public List<CshPaymentTrxRuleAsgnAe> queryCanAsgn(Long magOrgId, Long ruleId, IRequest request, Integer page, Integer pageSize) {
        if(page != null && pageSize != null) {
            PageHelper.startPage(page, pageSize);
        }
        return cshPaymentTrxRuleAsgnAeMapper.queryCanAsgn(magOrgId,ruleId);
    }
}