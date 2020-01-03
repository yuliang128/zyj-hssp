package com.hand.hec.mod.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.hand.hec.mod.dto.ModEntityReference;
import com.hand.hec.mod.service.IModEntityReferenceService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class ModEntityReferenceServiceImpl extends BaseServiceImpl<ModEntityReference> implements IModEntityReferenceService{

}