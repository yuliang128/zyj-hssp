package com.hand.hec.ssc.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.hand.hec.ssc.dto.SscTaskPoolHis;
import com.hand.hec.ssc.service.ISscTaskPoolHisService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class SscTaskPoolHisServiceImpl extends BaseServiceImpl<SscTaskPoolHis> implements ISscTaskPoolHisService{

}