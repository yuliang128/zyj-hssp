package com.hand.hec.exp.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.hand.hec.exp.dto.ExpOrgUnitTypeRelation;
import com.hand.hec.exp.service.IExpOrgUnitTypeRelationService;
import org.springframework.transaction.annotation.Transactional;
/**
 * <p>
 *  ExpOrgUnitTypeRelationServiceImpl
 * </p>
 * 
 * @author yang.duan 2019/01/10 11:19
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ExpOrgUnitTypeRelationServiceImpl extends BaseServiceImpl<ExpOrgUnitTypeRelation> implements IExpOrgUnitTypeRelationService{

}