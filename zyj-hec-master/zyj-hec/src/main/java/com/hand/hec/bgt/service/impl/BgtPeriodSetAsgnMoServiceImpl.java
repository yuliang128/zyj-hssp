package com.hand.hec.bgt.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.hand.hec.bgt.dto.BgtPeriodSetAsgnMo;
import com.hand.hec.bgt.service.IBgtPeriodSetAsgnMoService;
import org.springframework.transaction.annotation.Transactional;
/**
 * <p>
 * 预算期间集关联管理组织ServiceImpl
 * </p>
 * 
 * @author mouse 2019/01/07 16:52
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class BgtPeriodSetAsgnMoServiceImpl extends BaseServiceImpl<BgtPeriodSetAsgnMo> implements IBgtPeriodSetAsgnMoService{

}