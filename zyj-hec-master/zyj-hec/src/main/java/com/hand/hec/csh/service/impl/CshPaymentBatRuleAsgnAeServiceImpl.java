package com.hand.hec.csh.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hand.hec.csh.dto.CshPaymentBatRuleAsgnAe;
import com.hand.hec.csh.mapper.CshPaymentBatRuleAsgnAeMapper;
import com.hand.hec.csh.service.ICshPaymentBatRuleAsgnAeService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class CshPaymentBatRuleAsgnAeServiceImpl extends BaseServiceImpl<CshPaymentBatRuleAsgnAe> implements ICshPaymentBatRuleAsgnAeService{

    @Autowired
    private CshPaymentBatRuleAsgnAeMapper cshPaymentBatRuleAsgnAeMapper;


    @Override
    public List<CshPaymentBatRuleAsgnAeMapper> quertAccCanAsgn(Long magOrgId, Long ruleId, IRequest request, Integer page, Integer pageSize) {
        if(page != null && pageSize != null) {
            PageHelper.startPage(page, pageSize);
        }
        return cshPaymentBatRuleAsgnAeMapper.quertAccCanAsgn(magOrgId,ruleId);
    }
}