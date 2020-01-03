package com.hand.hec.exp.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.hand.hec.exp.dto.ExpMoExpPolicyAssignDtl;
import com.hand.hec.exp.service.IExpMoExpPolicyAssignDtlService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class ExpMoExpPolicyAssignDtlServiceImpl extends BaseServiceImpl<ExpMoExpPolicyAssignDtl> implements IExpMoExpPolicyAssignDtlService{

}