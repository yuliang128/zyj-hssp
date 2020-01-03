package com.hand.hec.fnd.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.fnd.dto.OrdSystemCustomer;
import com.hand.hec.fnd.exception.OrdSystemCustomerException;

import java.util.List;

/**
 * <p>
 * IOrdSystemCustomerService
 * </p>
 *
 * @author guiyuting 2019/02/22 17:01
 */
public interface IOrdSystemCustomerService
        extends IBaseService<OrdSystemCustomer>, ProxySelf<IOrdSystemCustomerService> {

    /**
     * 根据核算主体查询客户信息
     *
     * @param ordSystemCustomer
     * @return 客户信息
     * @author guiyuting 2019-02-20 16:43
     */
    List<OrdSystemCustomer> queryByAccEntity(IRequest request, OrdSystemCustomer ordSystemCustomer, int page,
                                             int pageSize);

    /**
     * 批量提交客户信息
     *
     * @param request
     * @param systemCustomers
     * @return 更新完成的客户信息
     * @author guiyuting 2019-02-20 17:00
     */
    List<OrdSystemCustomer> submitOrdAeCustomer(IRequest request, List<OrdSystemCustomer> systemCustomers) throws OrdSystemCustomerException;

    /**
     * 批量提交系统级客户信息
     *
     * @param request
     * @param systemCustomers
     * @return 更新完成的客户信息
     * @author guiyuting 2019-02-20 17:00
     */
    List<OrdSystemCustomer> submitOrdSysCustomer(IRequest request, List<OrdSystemCustomer> systemCustomers) throws OrdSystemCustomerException;

    /**
     * 系统级客户批量分配核算主体
     *
     * @param request
     * @param customerRefAe
     * @return
     * @author guiyuting 2019-02-21 16:43
     */
    List<OrdSystemCustomer> batchAssignAccEntity(IRequest request, List<OrdSystemCustomer> customerRefAe) throws OrdSystemCustomerException;

}
