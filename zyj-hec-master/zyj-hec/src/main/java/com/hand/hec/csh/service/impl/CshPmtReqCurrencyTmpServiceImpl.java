package com.hand.hec.csh.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.hand.hec.csh.dto.CshPmtReqCurrencyTmp;
import com.hand.hec.csh.service.ICshPmtReqCurrencyTmpService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class CshPmtReqCurrencyTmpServiceImpl extends BaseServiceImpl<CshPmtReqCurrencyTmp> implements ICshPmtReqCurrencyTmpService{

}