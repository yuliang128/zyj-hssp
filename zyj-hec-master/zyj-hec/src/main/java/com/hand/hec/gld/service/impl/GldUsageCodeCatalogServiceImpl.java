package com.hand.hec.gld.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.hand.hec.gld.dto.GldUsageCodeCatalog;
import com.hand.hec.gld.service.IGldUsageCodeCatalogService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class GldUsageCodeCatalogServiceImpl extends BaseServiceImpl<GldUsageCodeCatalog> implements IGldUsageCodeCatalogService{

}