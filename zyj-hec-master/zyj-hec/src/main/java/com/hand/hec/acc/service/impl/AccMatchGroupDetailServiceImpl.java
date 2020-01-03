package com.hand.hec.acc.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.hand.hec.acc.dto.AccMatchGroupDetail;
import com.hand.hec.acc.service.IAccMatchGroupDetailService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class AccMatchGroupDetailServiceImpl extends BaseServiceImpl<AccMatchGroupDetail> implements IAccMatchGroupDetailService{

}