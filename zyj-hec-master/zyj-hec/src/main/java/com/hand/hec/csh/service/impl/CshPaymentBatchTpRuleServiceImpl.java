package com.hand.hec.csh.service.impl;

import java.util.List;

import com.github.pagehelper.PageHelper;
import com.hand.hec.csh.mapper.CshPaymentBatchTpRuleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.csh.dto.CshPaymentBatchTpRule;
import com.hand.hec.csh.service.ICshPaymentBatchTpRuleService;

@Service
@Transactional(rollbackFor = Exception.class)
public class CshPaymentBatchTpRuleServiceImpl extends BaseServiceImpl<CshPaymentBatchTpRule> implements ICshPaymentBatchTpRuleService{
    @Autowired
    private CshPaymentBatchTpRuleMapper mapper;

    @Override
    public List<CshPaymentBatchTpRule> query(IRequest iRequest, CshPaymentBatchTpRule dto, int page, int pageSize){
        PageHelper.startPage(page,pageSize);
        return mapper.query(dto);
    }
}