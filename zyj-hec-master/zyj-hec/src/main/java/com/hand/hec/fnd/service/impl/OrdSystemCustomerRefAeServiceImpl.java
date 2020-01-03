package com.hand.hec.fnd.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.DTOStatus;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.fnd.exception.OrdSystemCustomerException;
import com.hand.hec.fnd.mapper.OrdSystemCustomerRefAeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hand.hec.fnd.dto.OrdSystemCustomerRefAe;
import com.hand.hec.fnd.service.IOrdSystemCustomerRefAeService;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * OrdCustomerAccountServiceImpl
 * </p>
 * 
 * @author guiyuting 2019/02/22 17:00
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class OrdSystemCustomerRefAeServiceImpl extends BaseServiceImpl<OrdSystemCustomerRefAe>
                implements IOrdSystemCustomerRefAeService {
    @Autowired
    private OrdSystemCustomerRefAeMapper customerRefAeMapper;

    @Override
    public OrdSystemCustomerRefAe submitByCustomer(IRequest request, OrdSystemCustomerRefAe customerRefAe)
                    throws OrdSystemCustomerException {
        OrdSystemCustomerRefAe refAe = new OrdSystemCustomerRefAe();
        switch (customerRefAe.get__status()) {
            case DTOStatus.ADD:
                // 校验当前客户是否已分配该核算主体
                checkCustomerAlreadyAssign(customerRefAe);
                refAe = self().insertSelective(request, customerRefAe);
                break;
            case DTOStatus.UPDATE:
//                checkCustomerAlreadyAssign(customerRefAe);
                refAe = self().updateByPrimaryKey(request, customerRefAe);
                break;
            case DTOStatus.DELETE:
                self().deleteByPrimaryKey(customerRefAe);
                break;
            default:
                break;
        }
        return refAe;
    }

    @Override
    public List<OrdSystemCustomerRefAe> batchAssignAccEntity(IRequest request,
                    List<OrdSystemCustomerRefAe> customerRefAes) throws OrdSystemCustomerException {
        List<OrdSystemCustomerRefAe> list = new ArrayList<>();
        for (OrdSystemCustomerRefAe customerRefAe : customerRefAes) {
            list.add(self().submitByCustomer(request, customerRefAe));
        }
        return list;
    }

    /**
     * 校验当前客户是否已分配该核算主体
     *
     * @param customerRefAe
     * @author guiyuting 2019-03-04 10:03
     * @return
     */
    private void checkCustomerAlreadyAssign(OrdSystemCustomerRefAe customerRefAe) throws OrdSystemCustomerException {
        OrdSystemCustomerRefAe testCustomerRefAe = new OrdSystemCustomerRefAe();
        testCustomerRefAe.setAccEntityId(customerRefAe.getAccEntityId());
        testCustomerRefAe.setCustomerId(customerRefAe.getCustomerId());
        int testNum = customerRefAeMapper.selectCount(testCustomerRefAe);
        if (testNum > 0) {
            throw new OrdSystemCustomerException(OrdSystemCustomerException.ACC_ENTITY_ERROR, null);
        }
    }
}
