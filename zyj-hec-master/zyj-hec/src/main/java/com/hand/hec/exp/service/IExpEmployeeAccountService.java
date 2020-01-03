package com.hand.hec.exp.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.exp.dto.ExpEmployeeAccount;

import java.util.List;

/**
 * <p>
 * 员工分配银行账户Service
 * </p>
 *
 * @author xiuxian.wu 2019/01/25 14:48
 */
public interface IExpEmployeeAccountService
                extends IBaseService<ExpEmployeeAccount>, ProxySelf<IExpEmployeeAccountService> {
    List<ExpEmployeeAccount> queryGldPayeeV(IRequest request, String payeeCategory, Long payeeId, Long accEntityId);
}
