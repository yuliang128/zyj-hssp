package com.hand.hec.csh.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.hand.hec.csh.dto.CshBankContactPerson;
import com.hand.hec.csh.service.ICshBankContactPersonService;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 公司付款账户分配联系人ServiceImpl
 * </p>
 *
 * @author xiuxian.xu 2019-03-15 9:36
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class CshBankContactPersonServiceImpl extends BaseServiceImpl<CshBankContactPerson> implements ICshBankContactPersonService {
    @Override
    protected boolean useSelectiveUpdate() {
        return false;
    }
}