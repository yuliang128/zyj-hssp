package com.hand.hec.mod.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.hand.hec.mod.dto.ModField;
import com.hand.hec.mod.service.IModFieldService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class ModFieldServiceImpl extends BaseServiceImpl<ModField> implements IModFieldService{

}