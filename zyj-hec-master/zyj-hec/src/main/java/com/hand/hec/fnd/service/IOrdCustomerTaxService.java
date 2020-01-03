package com.hand.hec.fnd.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.fnd.dto.OrdCustomerTax;
import com.hand.hec.fnd.exception.OrdSystemCustomerException;

import java.util.List;

/**
 * <p>
 * IOrdCustomerTaxService
 * </p>
 *
 * @author guiyuting 2019/02/22 16:56
 */
public interface IOrdCustomerTaxService extends IBaseService<OrdCustomerTax>, ProxySelf<IOrdCustomerTaxService> {

    /**
     * 提交税务信息
     *
     * @param request
     * @param customerTaxList
     * @return
     * @author guiyuting 2019-02-20 20:11
     */
    List<OrdCustomerTax> submitByAeCustomer(IRequest request, List<OrdCustomerTax> customerTaxList) throws OrdSystemCustomerException;

}