package com.hand.hec.exp.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.hand.hec.exp.dto.ExpOrgUnitType;
import com.hand.hec.exp.service.IExpOrgUnitTypeService;
import org.springframework.transaction.annotation.Transactional;
/**
 * <p>
 *  ExpOrgUnitTypeServiceImpl
 * </p>
 * 
 * @author yang.duan 2019/01/10 11:19
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ExpOrgUnitTypeServiceImpl extends BaseServiceImpl<ExpOrgUnitType> implements IExpOrgUnitTypeService{

}