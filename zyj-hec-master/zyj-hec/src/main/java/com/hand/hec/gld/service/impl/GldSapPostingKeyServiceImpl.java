package com.hand.hec.gld.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.hand.hec.gld.dto.GldSapPostingKey;
import com.hand.hec.gld.service.IGldSapPostingKeyService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class GldSapPostingKeyServiceImpl extends BaseServiceImpl<GldSapPostingKey> implements IGldSapPostingKeyService{

}