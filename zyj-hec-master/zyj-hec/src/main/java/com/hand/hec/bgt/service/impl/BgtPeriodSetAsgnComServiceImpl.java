package com.hand.hec.bgt.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.hand.hec.bgt.dto.BgtPeriodSetAsgnCom;
import com.hand.hec.bgt.service.IBgtPeriodSetAsgnComService;
import org.springframework.transaction.annotation.Transactional;
/**
 * <p>
 * 预算期间集关联公司ServiceImpl
 * </p>
 * 
 * @author mouse 2019/01/07 16:52
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class BgtPeriodSetAsgnComServiceImpl extends BaseServiceImpl<BgtPeriodSetAsgnCom> implements IBgtPeriodSetAsgnComService{

}