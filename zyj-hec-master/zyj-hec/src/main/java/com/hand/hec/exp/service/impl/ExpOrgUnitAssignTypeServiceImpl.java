package com.hand.hec.exp.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.hand.hec.exp.dto.ExpOrgUnitAssignType;
import com.hand.hec.exp.service.IExpOrgUnitAssignTypeService;
import org.springframework.transaction.annotation.Transactional;
/**
 * <p>
 * ExpOrgUnitAssignTypeServiceImpl
 * </p>
 * 
 * @author yang.duan 2019/01/10 11:18
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ExpOrgUnitAssignTypeServiceImpl extends BaseServiceImpl<ExpOrgUnitAssignType> implements IExpOrgUnitAssignTypeService{

}