package com.hand.hec.gld.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.hand.hec.gld.dto.GldSegmentValue;
import com.hand.hec.gld.service.IGldSegmentValueService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class GldSegmentValueServiceImpl extends BaseServiceImpl<GldSegmentValue> implements IGldSegmentValueService {

}