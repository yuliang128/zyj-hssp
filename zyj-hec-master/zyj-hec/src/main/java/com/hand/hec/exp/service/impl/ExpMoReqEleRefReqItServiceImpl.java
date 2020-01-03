package com.hand.hec.exp.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.hand.hec.exp.dto.ExpMoReqEleRefReqIt;
import com.hand.hec.exp.service.IExpMoReqEleRefReqItService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class ExpMoReqEleRefReqItServiceImpl extends BaseServiceImpl<ExpMoReqEleRefReqIt> implements IExpMoReqEleRefReqItService{

}