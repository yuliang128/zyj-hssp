package com.hand.hec.fnd.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.fnd.dto.OrdSystemCustomerRefAe;
import com.hand.hec.fnd.exception.OrdSystemCustomerException;

import java.util.List;

/**
 * <p>
 * IOrdSystemCustomerRefAeService
 * </p>
 *
 * @author guiyuting 2019/02/22 16:56
 */
public interface IOrdSystemCustomerRefAeService extends IBaseService<OrdSystemCustomerRefAe>, ProxySelf<IOrdSystemCustomerRefAeService> {

    /**
     * 保存客户信息与核算主体的联系
     *
     * @param request
     * @param customerRefAe
     * @return 保存成功的数据
     * @author guiyuting 2019-02-20 18:15
     */
    OrdSystemCustomerRefAe submitByCustomer(IRequest request, OrdSystemCustomerRefAe customerRefAe) throws OrdSystemCustomerException;

    /**
     * 系统级客户批量分配核算主体
     *
     * @param request
     * @param customerRefAe
     * @return
     * @author guiyuting 2019-02-21 16:43
     */
    List<OrdSystemCustomerRefAe> batchAssignAccEntity(IRequest request, List<OrdSystemCustomerRefAe> customerRefAe) throws OrdSystemCustomerException;

}