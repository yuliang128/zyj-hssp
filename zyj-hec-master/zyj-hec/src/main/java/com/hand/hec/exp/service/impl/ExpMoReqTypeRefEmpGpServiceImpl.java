package com.hand.hec.exp.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.hand.hec.exp.dto.ExpMoReqTypeRefEmpGp;
import com.hand.hec.exp.service.IExpMoReqTypeRefEmpGpService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class ExpMoReqTypeRefEmpGpServiceImpl extends BaseServiceImpl<ExpMoReqTypeRefEmpGp> implements IExpMoReqTypeRefEmpGpService{

}