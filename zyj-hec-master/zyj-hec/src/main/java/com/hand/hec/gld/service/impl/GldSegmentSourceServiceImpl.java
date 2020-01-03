package com.hand.hec.gld.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.hand.hec.gld.dto.GldSegmentSource;
import com.hand.hec.gld.service.IGldSegmentSourceService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class GldSegmentSourceServiceImpl extends BaseServiceImpl<GldSegmentSource> implements IGldSegmentSourceService {

}