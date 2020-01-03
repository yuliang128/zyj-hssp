package com.hand.hec.acc.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.hand.hec.acc.dto.AccBatch;
import com.hand.hec.acc.service.IAccBatchService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class AccBatchServiceImpl extends BaseServiceImpl<AccBatch> implements IAccBatchService{

}