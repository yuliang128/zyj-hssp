package com.hand.hec.mod.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.hand.hec.mod.dto.ModModel;
import com.hand.hec.mod.service.IModModelService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class ModModelServiceImpl extends BaseServiceImpl<ModModel> implements IModModelService{

}