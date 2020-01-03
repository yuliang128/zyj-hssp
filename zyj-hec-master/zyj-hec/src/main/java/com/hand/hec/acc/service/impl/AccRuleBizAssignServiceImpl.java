package com.hand.hec.acc.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.hand.hec.acc.dto.AccRuleBizAssign;
import com.hand.hec.acc.service.IAccRuleBizAssignService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class AccRuleBizAssignServiceImpl extends BaseServiceImpl<AccRuleBizAssign> implements IAccRuleBizAssignService{

}