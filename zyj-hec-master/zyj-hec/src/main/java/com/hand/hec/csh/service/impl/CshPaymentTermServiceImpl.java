package com.hand.hec.csh.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.hand.hec.csh.dto.CshPaymentTerm;
import com.hand.hec.csh.service.ICshPaymentTermService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class CshPaymentTermServiceImpl extends BaseServiceImpl<CshPaymentTerm> implements ICshPaymentTermService{

}