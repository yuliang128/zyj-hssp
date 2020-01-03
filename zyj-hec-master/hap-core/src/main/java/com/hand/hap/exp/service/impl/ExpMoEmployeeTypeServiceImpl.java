package com.hand.hap.exp.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hand.hap.exp.dto.ExpMoEmployeeType;
import com.hand.hap.exp.service.IExpMoEmployeeTypeService;
import com.hand.hap.system.service.impl.BaseServiceImpl;

@Service
@Transactional(rollbackFor = Exception.class)
public class ExpMoEmployeeTypeServiceImpl extends BaseServiceImpl<ExpMoEmployeeType> implements IExpMoEmployeeTypeService {

}