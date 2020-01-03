package com.hand.hec.fnd.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.fnd.dto.OrdCustomerAccount;
import com.hand.hec.fnd.exception.OrdSystemCustomerException;

import java.util.List;

/**
 * <p>
 * IOrdCustomerAccountService
 * </p>
 * 
 * @author guiyuting 2019/02/22 16:55
 */

public interface IOrdCustomerAccountService extends IBaseService<OrdCustomerAccount>, ProxySelf<IOrdCustomerAccountService>{
    /**
     *
     *
     * 批量保存客户银行账号
     * @param request
     * @param customerAccounts
     * @param accEntityId
     * @author guiyuting 2019-02-20 19:13
     * @return 
     */

    List<OrdCustomerAccount> submitByCustomer(IRequest request,List<OrdCustomerAccount> customerAccounts) throws OrdSystemCustomerException;
    
    /**
     * 
     *根据核算主体查询账户信息
     *
     * @param request
     * @param customerAccount
     * @param page
     * @param pageSize
     * @author guiyuting 2019-02-21 10:45
     * @return
     */
    List<OrdCustomerAccount> queryByAccEntity(IRequest request,OrdCustomerAccount customerAccount,int page,int pageSize);

    /**
     * 系统级客户查询账户信息
     *
     * @param customerAccount
     * @author guiyuting 2019-02-22 14:06
     * @return
     */
    List<OrdCustomerAccount> queryBySysCustomer(IRequest request,OrdCustomerAccount customerAccount,int page,int pageSize);

}