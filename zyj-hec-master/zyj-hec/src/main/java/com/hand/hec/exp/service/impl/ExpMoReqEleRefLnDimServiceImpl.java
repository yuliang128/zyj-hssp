package com.hand.hec.exp.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.hand.hec.exp.dto.ExpMoReqEleRefLnDim;
import com.hand.hec.exp.service.IExpMoReqEleRefLnDimService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class ExpMoReqEleRefLnDimServiceImpl extends BaseServiceImpl<ExpMoReqEleRefLnDim> implements IExpMoReqEleRefLnDimService{

}