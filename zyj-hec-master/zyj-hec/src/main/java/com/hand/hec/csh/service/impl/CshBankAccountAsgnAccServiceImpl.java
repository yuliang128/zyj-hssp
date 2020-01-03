package com.hand.hec.csh.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.hand.hec.csh.dto.CshBankAccountAsgnAcc;
import com.hand.hec.csh.service.ICshBankAccountAsgnAccService;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 公司付款账户分配核算科目ServiceImpl
 * </p>
 *
 * @author xiuxian.xu 2019-03-15 9:36
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class CshBankAccountAsgnAccServiceImpl extends BaseServiceImpl<CshBankAccountAsgnAcc> implements ICshBankAccountAsgnAccService {

}