package com.hand.hec.csh.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.hand.hec.csh.dto.CshPaymentReqAccountTmp;
import com.hand.hec.csh.service.ICshPaymentReqAccountTmpService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class CshPaymentReqAccountTmpServiceImpl extends BaseServiceImpl<CshPaymentReqAccountTmp> implements ICshPaymentReqAccountTmpService{

}