package com.hand.hec.fnd.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.DTOStatus;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.fnd.dto.OrdCustomerAccountRefAe;
import com.hand.hec.fnd.dto.OrdCustomerTaxRefAe;
import com.hand.hec.fnd.exception.OrdSystemCustomerException;
import com.hand.hec.fnd.mapper.OrdCustomerAccountRefAeMapper;
import com.hand.hec.fnd.mapper.OrdCustomerTaxRefAeMapper;
import com.hand.hec.fnd.service.IOrdCustomerTaxRefAeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hand.hec.fnd.dto.OrdCustomerTax;
import com.hand.hec.fnd.service.IOrdCustomerTaxService;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * OrdCustomerAccountServiceImpl
 * </p>
 *
 * @author guiyuting 2019/02/22 16:59
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class OrdCustomerTaxServiceImpl extends BaseServiceImpl<OrdCustomerTax> implements IOrdCustomerTaxService {
    @Autowired
    private IOrdCustomerTaxRefAeService taxRefAeService;

    @Override
    public List<OrdCustomerTax> submitByAeCustomer(IRequest request, List<OrdCustomerTax> customerTaxList)
                    throws OrdSystemCustomerException {
        // 录入核算主体级客户税务信息
        List<OrdCustomerTax> list = new ArrayList<>();
        for (OrdCustomerTax customerTax : customerTaxList) {
            OrdCustomerTax tax = new OrdCustomerTax();
            switch (customerTax.get__status()) {
                case DTOStatus.ADD:
                    tax = self().insertSelective(request, customerTax);
                    // 录入核算主体信息
                    submitCustomerTaxRefAe(request, tax);
                    break;
                case DTOStatus.UPDATE:
                    tax = self().updateByPrimaryKey(request, customerTax);
                    break;
                case DTOStatus.DELETE:
                    self().deleteByPrimaryKey(customerTax);
                    break;
                default:
                    break;
            }
            list.add(tax);
        }
        return list;
    }

    /**
     * 录入核算主体信息
     *
     * @param request
     * @param customerTax
     * @author guiyuting 2019-03-04 14:15
     * @return 
     */
    private void submitCustomerTaxRefAe(IRequest request, OrdCustomerTax customerTax)
                    throws OrdSystemCustomerException {
        OrdCustomerTaxRefAe taxRefAe = new OrdCustomerTaxRefAe();
        taxRefAe.setTaxId(customerTax.getTaxId());
        taxRefAe.setAccEntityId(customerTax.getAccEntityId());
        taxRefAe.set__status(customerTax.get__status());
        taxRefAeService.submitCustomerTaxRefAe(request, taxRefAe);
    }
}
