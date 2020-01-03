package com.hand.hec.fnd.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.hand.hec.fnd.dto.FndUomType;
import com.hand.hec.fnd.service.IFndUomTypeService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class FndUomTypeServiceImpl extends BaseServiceImpl<FndUomType> implements IFndUomTypeService{

}