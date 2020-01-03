package com.hand.hap.fnd.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hand.hap.fnd.dto.FndCompanyLevel;
import com.hand.hap.fnd.service.IFndCompanyLevelService;
import com.hand.hap.system.service.impl.BaseServiceImpl;

/**
 * <p>
 * FndCompanyLevelServiceImpl
 * </p>
 *
 * @author yang.duan 2019/01/10 11:07
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class FndCompanyLevelServiceImpl extends BaseServiceImpl<FndCompanyLevel> implements IFndCompanyLevelService {

}