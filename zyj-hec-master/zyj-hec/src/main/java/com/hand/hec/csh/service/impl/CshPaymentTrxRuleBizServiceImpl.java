package com.hand.hec.csh.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.hand.hec.csh.dto.CshPaymentTrxRuleBiz;
import com.hand.hec.csh.service.ICshPaymentTrxRuleBizService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class CshPaymentTrxRuleBizServiceImpl extends BaseServiceImpl<CshPaymentTrxRuleBiz> implements ICshPaymentTrxRuleBizService{

}