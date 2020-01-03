package com.hand.hec.gld.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.gld.dto.GldRespCenterHierarchy;
import com.hand.hec.gld.service.IGldRespCenterHierarchyService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class GldRespCenterHierarchyServiceImpl extends BaseServiceImpl<GldRespCenterHierarchy> implements
		IGldRespCenterHierarchyService {

}