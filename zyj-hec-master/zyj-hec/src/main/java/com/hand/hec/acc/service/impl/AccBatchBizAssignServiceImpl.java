package com.hand.hec.acc.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.hand.hec.acc.dto.AccBatchBizAssign;
import com.hand.hec.acc.service.IAccBatchBizAssignService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class AccBatchBizAssignServiceImpl extends BaseServiceImpl<AccBatchBizAssign> implements IAccBatchBizAssignService{

}