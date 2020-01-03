package com.hand.hec.csh.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.hand.hec.csh.dto.CshPaymentBatchRuleDtl;
import com.hand.hec.csh.service.ICshPaymentBatchRuleDtlService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class CshPaymentBatchRuleDtlServiceImpl extends BaseServiceImpl<CshPaymentBatchRuleDtl> implements ICshPaymentBatchRuleDtlService{
}