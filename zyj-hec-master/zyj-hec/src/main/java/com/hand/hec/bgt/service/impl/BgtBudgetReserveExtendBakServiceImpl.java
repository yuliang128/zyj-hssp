package com.hand.hec.bgt.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.hand.hec.bgt.dto.BgtBudgetReserveExtendBak;
import com.hand.hec.bgt.service.IBgtBudgetReserveExtendBakService;
import org.springframework.transaction.annotation.Transactional;
/**
 * <p>
 * 预算占用币种扩展备份ServiceImpl
 * </p>
 * 
 * @author mouse 2019/01/07 16:50
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class BgtBudgetReserveExtendBakServiceImpl extends BaseServiceImpl<BgtBudgetReserveExtendBak> implements IBgtBudgetReserveExtendBakService{

}