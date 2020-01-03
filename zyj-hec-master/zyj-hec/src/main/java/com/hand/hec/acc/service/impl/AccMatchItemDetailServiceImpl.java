package com.hand.hec.acc.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.hand.hec.acc.dto.AccMatchItemDetail;
import com.hand.hec.acc.service.IAccMatchItemDetailService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class AccMatchItemDetailServiceImpl extends BaseServiceImpl<AccMatchItemDetail> implements IAccMatchItemDetailService{

}