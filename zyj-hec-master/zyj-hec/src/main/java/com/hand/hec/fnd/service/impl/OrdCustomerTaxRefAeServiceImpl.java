package com.hand.hec.fnd.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.DTOStatus;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.fnd.exception.OrdSystemCustomerException;
import com.hand.hec.fnd.mapper.OrdCustomerTaxRefAeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hand.hec.fnd.dto.OrdCustomerTaxRefAe;
import com.hand.hec.fnd.service.IOrdCustomerTaxRefAeService;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * OrdCustomerAccountServiceImpl
 * </p>
 * 
 * @author guiyuting 2019/02/22 16:59
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class OrdCustomerTaxRefAeServiceImpl extends BaseServiceImpl<OrdCustomerTaxRefAe>
                implements IOrdCustomerTaxRefAeService {

    @Autowired
    private OrdCustomerTaxRefAeMapper taxRefAeMapper;

    @Override
    public OrdCustomerTaxRefAe submitCustomerTaxRefAe(IRequest request, OrdCustomerTaxRefAe taxRefAe)
                    throws OrdSystemCustomerException {
        OrdCustomerTaxRefAe customerTaxRefAe = new OrdCustomerTaxRefAe();
        switch (taxRefAe.get__status()) {
            case DTOStatus.ADD:
                checkTaxAlreadyAssign(taxRefAe);
                customerTaxRefAe = self().insertSelective(request, taxRefAe);
                break;
            case DTOStatus.UPDATE:
                checkTaxAlreadyAssign(taxRefAe);
                customerTaxRefAe = self().updateByPrimaryKey(request, taxRefAe);
                break;
            case DTOStatus.DELETE:
                self().deleteByPrimaryKey(taxRefAe);
                break;
            default:
                break;
        }
        return customerTaxRefAe;
    }

    /**
     * 校验当前税务信息是否已分配该核算主体
     *
     * @param taxRefAe
     * @author guiyuting 2019-03-04 11:14
     * @return
     */
    private void checkTaxAlreadyAssign(OrdCustomerTaxRefAe taxRefAe) throws OrdSystemCustomerException {
        OrdCustomerTaxRefAe customerTaxRefAe = new OrdCustomerTaxRefAe();
        customerTaxRefAe.setAccEntityId(taxRefAe.getAccEntityId());
        customerTaxRefAe.setTaxId(taxRefAe.getTaxId());
        int num = taxRefAeMapper.selectCount(customerTaxRefAe);
        if (num > 0) {
            throw new OrdSystemCustomerException(OrdSystemCustomerException.TAX_ACC_ENTITY_ERROR, null);
        }
    }
}
