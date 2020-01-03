package com.hand.hec.fnd.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.hand.hec.fnd.dto.FndCountryCode;
import com.hand.hec.fnd.service.IFndCountryCodeService;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * FndCountryCodeServiceImpl
 * </p>
 *
 * @author guiyu 2019/01/29 15:29
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class FndCountryCodeServiceImpl extends BaseServiceImpl<FndCountryCode> implements IFndCountryCodeService{

}