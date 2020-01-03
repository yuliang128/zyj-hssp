package com.hand.hec.csh.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.hand.hec.csh.dto.CshPaymentBatchRuleLn;
import com.hand.hec.csh.service.ICshPaymentBatchRuleLnService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class CshPaymentBatchRuleLnServiceImpl extends BaseServiceImpl<CshPaymentBatchRuleLn> implements ICshPaymentBatchRuleLnService{

}