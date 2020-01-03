package com.hand.hec.exp.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.hand.hec.exp.dto.ExpMoRepTypeRefExpIt;
import com.hand.hec.exp.service.IExpMoRepTypeRefExpItService;
import org.springframework.transaction.annotation.Transactional;
/**
 * <p>
 * ExpMoRepTypeRefExpItServiceImpl
 * </p>
 *
 * @author yang.duan 2019/01/10 14:50
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ExpMoRepTypeRefExpItServiceImpl extends BaseServiceImpl<ExpMoRepTypeRefExpIt> implements IExpMoRepTypeRefExpItService{

}