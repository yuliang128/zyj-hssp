package com.hand.hec.bgt.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.hand.hec.bgt.dto.BgtBudgetStrcAuthority;
import com.hand.hec.bgt.service.IBgtBudgetStrcAuthorityService;
import org.springframework.transaction.annotation.Transactional;
/**
 * <p>
 * 预算表授权ServiceImpl
 * </p>
 * 
 * @author mouse 2019/01/07 16:50
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class BgtBudgetStrcAuthorityServiceImpl extends BaseServiceImpl<BgtBudgetStrcAuthority> implements IBgtBudgetStrcAuthorityService{

}