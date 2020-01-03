package com.hand.hec.ssc.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.hand.hec.ssc.dto.SscTaskDispatchAdvice;
import com.hand.hec.ssc.service.ISscTaskDispatchAdviceService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class SscTaskDispatchAdviceServiceImpl extends BaseServiceImpl<SscTaskDispatchAdvice> implements ISscTaskDispatchAdviceService{

}