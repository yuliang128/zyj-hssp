package com.hand.hec.gld.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.hand.hec.gld.dto.GldRespCenterRefBc;
import com.hand.hec.gld.service.IGldRespCenterRefBcService;
import org.springframework.transaction.annotation.Transactional;
/**
 * <p>
 * GldRespCenterRefBcServiceImpl
 * </p>
 * 
 * @author yang.duan 2019/01/10 13:49
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class GldRespCenterRefBcServiceImpl extends BaseServiceImpl<GldRespCenterRefBc> implements IGldRespCenterRefBcService{

}