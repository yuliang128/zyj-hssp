package com.hand.hec.exp.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.hand.hec.exp.dto.ExpMoReqEleRefExpTp;
import com.hand.hec.exp.service.IExpMoReqEleRefExpTpService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class ExpMoReqEleRefExpTpServiceImpl extends BaseServiceImpl<ExpMoReqEleRefExpTp> implements IExpMoReqEleRefExpTpService{

}