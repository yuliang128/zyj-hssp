package com.hand.hec.exp.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.hand.hec.exp.dto.ExpMoRepEleRefExpIt;
import com.hand.hec.exp.service.IExpMoRepEleRefExpItService;
import org.springframework.transaction.annotation.Transactional;
/**
 * <p>
 * ExpMoRepEleRefExpItServiceImpl
 * </p>
 *
 * @author yang.duan 2019/01/10 14:48
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ExpMoRepEleRefExpItServiceImpl extends BaseServiceImpl<ExpMoRepEleRefExpIt> implements IExpMoRepEleRefExpItService{

}