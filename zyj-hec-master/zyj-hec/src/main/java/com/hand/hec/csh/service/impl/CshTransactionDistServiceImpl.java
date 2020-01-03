package com.hand.hec.csh.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.hand.hec.csh.dto.CshTransactionDist;
import com.hand.hec.csh.service.ICshTransactionDistService;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 现金事务分配行ServiceImpl
 * </p>
 * 
 * @author Tagin 2019/01/22 20:03
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class CshTransactionDistServiceImpl extends BaseServiceImpl<CshTransactionDist>
                implements ICshTransactionDistService {

}
