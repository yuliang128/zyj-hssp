package com.hand.hec.csh.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.hand.hec.csh.dto.CshBankBranch;
import com.hand.hec.csh.service.ICshBankBranchService;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 公司付款账户定义ServiceImpl
 * </p>
 *
 * @author xiuxian.xu 2019-03-15 9:36
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class CshBankBranchServiceImpl extends BaseServiceImpl<CshBankBranch> implements ICshBankBranchService  {

    @Override
    protected boolean useSelectiveUpdate() {
        return false;
    }
}