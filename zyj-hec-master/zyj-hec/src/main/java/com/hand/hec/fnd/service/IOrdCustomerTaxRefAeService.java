package com.hand.hec.fnd.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.fnd.dto.OrdCustomerTaxRefAe;
import com.hand.hec.fnd.exception.OrdSystemCustomerException;

/**
 * <p>
 * IOrdCustomerTaxRefAeService
 * </p>
 *
 * @author guiyuting 2019/02/22 16:56
 */
public interface IOrdCustomerTaxRefAeService extends IBaseService<OrdCustomerTaxRefAe>, ProxySelf<IOrdCustomerTaxRefAeService> {

    /**
     * 客户税务信息分配更新
     *
     * @param request
     * @param taxRefAe
     * @return
     * @author guiyuting 2019-02-20 20:26
     */
    OrdCustomerTaxRefAe submitCustomerTaxRefAe(IRequest request, OrdCustomerTaxRefAe taxRefAe) throws OrdSystemCustomerException;
}