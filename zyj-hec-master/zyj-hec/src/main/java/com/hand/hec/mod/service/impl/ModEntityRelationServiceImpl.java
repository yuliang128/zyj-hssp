package com.hand.hec.mod.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.hand.hec.mod.dto.ModEntityRelation;
import com.hand.hec.mod.service.IModEntityRelationService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class ModEntityRelationServiceImpl extends BaseServiceImpl<ModEntityRelation> implements IModEntityRelationService{

}