package com.hand.hec.ssc.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.hand.hec.ssc.dto.SscNodeAsgnWorkCenter;
import com.hand.hec.ssc.service.ISscNodeAsgnWorkCenterService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class SscNodeAsgnWorkCenterServiceImpl extends BaseServiceImpl<SscNodeAsgnWorkCenter> implements ISscNodeAsgnWorkCenterService{

}