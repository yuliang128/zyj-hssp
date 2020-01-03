package com.hand.hec.gld.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.hand.hec.gld.dto.GldUsageCode;
import com.hand.hec.gld.service.IGldUsageCodeService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class GldUsageCodeServiceImpl extends BaseServiceImpl<GldUsageCode> implements IGldUsageCodeService{

}