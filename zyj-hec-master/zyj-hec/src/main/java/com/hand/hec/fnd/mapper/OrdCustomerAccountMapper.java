package com.hand.hec.fnd.mapper;

import com.hand.hap.core.IRequest;
import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.fnd.dto.OrdCustomerAccount;

import java.util.List;

/**
 * <p>
 * OrdCustomerAccountMapper
 * </p>
 *
 * @author guiyuting 2019/02/22 17:01
 */
public interface OrdCustomerAccountMapper extends Mapper<OrdCustomerAccount> {

    /**
     * 根据核算主体查询账户信息
     *
     * @param customerAccount
     * @return
     * @author guiyuting 2019-02-21 10:45
     */
    List<OrdCustomerAccount> queryByAccEntity(OrdCustomerAccount customerAccount);

    /**
     * 系统级客户查询账户信息
     *
     * @param customerAccount
     * @return
     * @author guiyuting 2019-02-22 14:06
     */
    List<OrdCustomerAccount> queryBySysCustomer(OrdCustomerAccount customerAccount);


}
