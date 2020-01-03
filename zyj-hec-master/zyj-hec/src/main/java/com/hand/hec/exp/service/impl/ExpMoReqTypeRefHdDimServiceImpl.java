package com.hand.hec.exp.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.hand.hec.exp.dto.ExpMoReqTypeRefHdDim;
import com.hand.hec.exp.service.IExpMoReqTypeRefHdDimService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class ExpMoReqTypeRefHdDimServiceImpl extends BaseServiceImpl<ExpMoReqTypeRefHdDim> implements IExpMoReqTypeRefHdDimService{

}