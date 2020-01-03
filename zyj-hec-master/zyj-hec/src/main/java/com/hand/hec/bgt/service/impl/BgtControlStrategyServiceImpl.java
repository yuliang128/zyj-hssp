package com.hand.hec.bgt.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.hand.hec.bgt.dto.BgtControlStrategy;
import com.hand.hec.bgt.service.IBgtControlStrategyService;
import org.springframework.transaction.annotation.Transactional;
/**
 * <p>
 * 预算控制策略ServiceImpl
 * </p>
 * 
 * @author mouse 2019/01/07 16:51
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class BgtControlStrategyServiceImpl extends BaseServiceImpl<BgtControlStrategy> implements IBgtControlStrategyService{

}