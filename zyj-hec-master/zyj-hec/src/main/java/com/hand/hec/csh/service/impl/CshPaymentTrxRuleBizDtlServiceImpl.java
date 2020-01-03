package com.hand.hec.csh.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.hand.hec.csh.dto.CshPaymentTrxRuleBizDtl;
import com.hand.hec.csh.service.ICshPaymentTrxRuleBizDtlService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class CshPaymentTrxRuleBizDtlServiceImpl extends BaseServiceImpl<CshPaymentTrxRuleBizDtl> implements ICshPaymentTrxRuleBizDtlService{

}