package com.hand.hec.exp.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.exp.dto.ExpEmployeeAccount;
import com.hand.hec.exp.mapper.ExpEmployeeAccountMapper;
import com.hand.hec.exp.service.IExpEmployeeAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 员工分配银行账户ServiceImpl
 * </p>
 *
 * @author xiuxian.wu 2019/01/25 14:48
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ExpEmployeeAccountServiceImpl extends BaseServiceImpl<ExpEmployeeAccount>
                implements IExpEmployeeAccountService {
    @Autowired
    private ExpEmployeeAccountMapper employeeAccountMapper;

    @Override
    protected boolean useSelectiveUpdate() {
        return false;
    }

    @Override
    public List<ExpEmployeeAccount> queryGldPayeeV(IRequest request, String payeeCategory, Long payeeId,
                    Long accEntityId) {
        return employeeAccountMapper.queryGldPayeeV(payeeCategory, payeeId, accEntityId);
    }
}
