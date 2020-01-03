package com.hand.hec.expm.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.hand.hec.expm.dto.ExpAmortizationAccount;
import com.hand.hec.expm.service.IExpAmortizationAccountService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class ExpAmortizationAccountServiceImpl extends BaseServiceImpl<ExpAmortizationAccount> implements IExpAmortizationAccountService{

}