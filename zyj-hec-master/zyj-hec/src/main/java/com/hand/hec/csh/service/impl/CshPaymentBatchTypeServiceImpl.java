package com.hand.hec.csh.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.csh.dto.CshPaymentBatchType;
import com.hand.hec.csh.service.ICshPaymentBatchTypeService;

@Service
@Transactional(rollbackFor = Exception.class)
public class CshPaymentBatchTypeServiceImpl extends BaseServiceImpl<CshPaymentBatchType>
                implements ICshPaymentBatchTypeService {

}
