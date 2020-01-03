package com.hand.hec.fnd.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.fnd.dto.OrdCustomerAccountRefAe;
import com.hand.hec.fnd.exception.OrdSystemCustomerException;

import java.util.List;

/**
 * <p>
 * IOrdCustomerAccountRefAeService
 * </p>
 * 
 * @author guiyuting 2019/02/22 16:55
 */

public interface IOrdCustomerAccountRefAeService
                extends IBaseService<OrdCustomerAccountRefAe>, ProxySelf<IOrdCustomerAccountRefAeService> {

    /**
     * 更新账户与核算主体关联信息
     *
     * @param accountRefAe
     * @author guiyuting 2019-02-21 11:04
     * @return null
     */

    void updateByAccEntity(IRequest request, OrdCustomerAccountRefAe accountRefAe);

    /**
     * 系统级客户录入核算主体信息
     *
     * @param request
     * @param customerAccountRefAes
     * @author guiyuting 2019-02-20 19:20
     * @return
     */
    List<OrdCustomerAccountRefAe> submitSysCustomerAccountRefAe(IRequest request,
                    List<OrdCustomerAccountRefAe> customerAccountRefAes) throws OrdSystemCustomerException;

}
