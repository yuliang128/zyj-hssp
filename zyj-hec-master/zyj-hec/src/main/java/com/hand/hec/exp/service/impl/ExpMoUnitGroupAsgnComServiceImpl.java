package com.hand.hec.exp.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.hand.hec.exp.dto.ExpMoUnitGroupAsgnCom;
import com.hand.hec.exp.service.IExpMoUnitGroupAsgnComService;
import org.springframework.transaction.annotation.Transactional;
/**
 * <p>
 * ExpMoUnitGroupAsgnComServiceImpl
 * </p>
 * 
 * @author yang.duan 2019/01/10 11:26
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ExpMoUnitGroupAsgnComServiceImpl extends BaseServiceImpl<ExpMoUnitGroupAsgnCom> implements IExpMoUnitGroupAsgnComService{

}