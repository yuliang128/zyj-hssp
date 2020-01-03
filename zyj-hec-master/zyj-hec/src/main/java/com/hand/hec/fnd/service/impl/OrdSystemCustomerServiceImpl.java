package com.hand.hec.fnd.service.impl;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.DTOStatus;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.fnd.dto.OrdSystemCustomerRefAe;
import com.hand.hec.fnd.exception.OrdSystemCustomerException;
import com.hand.hec.fnd.mapper.OrdSystemCustomerMapper;
import com.hand.hec.fnd.mapper.OrdSystemCustomerRefAeMapper;
import com.hand.hec.fnd.service.IOrdSystemCustomerRefAeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hand.hec.fnd.dto.OrdSystemCustomer;
import com.hand.hec.fnd.service.IOrdSystemCustomerService;
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
public class OrdSystemCustomerServiceImpl extends BaseServiceImpl<OrdSystemCustomer>
                implements IOrdSystemCustomerService {
    @Autowired
    private OrdSystemCustomerMapper customerMapper;

    @Autowired
    private IOrdSystemCustomerRefAeService customerRefAeService;

    @Autowired
    private OrdSystemCustomerRefAeMapper customerRefAeMapper;

    @Override
    public List<OrdSystemCustomer> queryByAccEntity(IRequest request, OrdSystemCustomer ordSystemCustomer, int page,
                    int pageSize) {
        PageHelper.startPage(page, pageSize);
        return customerMapper.queryByAccEntity(ordSystemCustomer);
    }

    @Override
    public List<OrdSystemCustomer> submitOrdAeCustomer(IRequest request, List<OrdSystemCustomer> systemCustomers)
                    throws OrdSystemCustomerException {
        // 录入客户信息
        List<OrdSystemCustomer> resultList = self().submitOrdSysCustomer(request, systemCustomers);
        // 录入核算主体信息
        submitCustomerRefAe(request, resultList);
        return resultList;
    }

    @Override
    public List<OrdSystemCustomer> submitOrdSysCustomer(IRequest request, List<OrdSystemCustomer> systemCustomers)
                    throws OrdSystemCustomerException {
        List<OrdSystemCustomer> result = new ArrayList<>();
        for (OrdSystemCustomer customer : systemCustomers) {
            OrdSystemCustomer testCustomer = new OrdSystemCustomer();
            switch (customer.get__status()) {
                case DTOStatus.ADD:
                    // 判断客户是否被其他核算主体维护
                    checkCustomerAccEntity(customer);
                    // 校验客户代码是否重复
                    checkCustomerCodeUnique(customer);
                    testCustomer = self().insertSelective(request, customer);
                    break;
                case DTOStatus.UPDATE:
                    testCustomer = self().updateByPrimaryKey(request, customer);
                    break;
                case DTOStatus.DELETE:
                    self().deleteByPrimaryKey(customer);
                    break;
                default:
                    break;
            }

            result.add(testCustomer);
        }
        return result;
    }

    @Override
    public List<OrdSystemCustomer> batchAssignAccEntity(IRequest request, List<OrdSystemCustomer> list)
                    throws OrdSystemCustomerException {
        List<OrdSystemCustomerRefAe> refAeList = new ArrayList<>();
        for (OrdSystemCustomer systemCustomer : list) {
            List<OrdSystemCustomerRefAe> customerRefAes = systemCustomer.getAccEntityAssign();
            for (OrdSystemCustomerRefAe customerRefAe : customerRefAes) {
                customerRefAe.setCustomerId(systemCustomer.getCustomerId());
                // 校验当前核算主体是否已分配
                int num = customerRefAeMapper.selectCount(customerRefAe);
                if (num < 1) {
                    customerRefAe.setEnabledFlag(systemCustomer.getEnabledFlag());
                    customerRefAe.set__status(DTOStatus.ADD);
                    refAeList.add(customerRefAe);
                }

            }
        }
        customerRefAeService.batchUpdate(request, refAeList);
        return list;
    }

    /**
     * 判断客户是否被其他核算主体维护
     *
     * @param systemCustomer
     * @author guiyuting 2019-03-04 13:57
     * @return void
     */
    private void checkCustomerAccEntity(OrdSystemCustomer systemCustomer) throws OrdSystemCustomerException {
        OrdSystemCustomer testCustomer = new OrdSystemCustomer();
        testCustomer.setCustomerCode(systemCustomer.getCustomerCode());
        int alreadyNum = customerMapper.selectCount(testCustomer);
        if (alreadyNum > 0) {
            throw new OrdSystemCustomerException(OrdSystemCustomerException.EXISTS_CUSTOMER_ERROR, null);
        }
    }

    /**
     * 校验客户代码是否重复
     *
     * @param customer
     * @author guiyuting 2019-03-04 13:59
     * @return
     */
    private void checkCustomerCodeUnique(OrdSystemCustomer customer) throws OrdSystemCustomerException {
        OrdSystemCustomer testCustomer = new OrdSystemCustomer();
        testCustomer.setCustomerCode(customer.getCustomerCode());
        int testNum = customerMapper.selectCount(testCustomer);
        if (testNum > 0) {
            throw new OrdSystemCustomerException(OrdSystemCustomerException.CUSTOMER_CODE_ERROR, null);
        }
    }

    /**
     * 录入核算主体信息
     *
     * @param request
     * @param resultList
     * @author guiyuting 2019-03-04 14:30
     * @return
     */
    private void submitCustomerRefAe(IRequest request, List<OrdSystemCustomer> resultList)
                    throws OrdSystemCustomerException {
        List<OrdSystemCustomerRefAe> customerRefAes = new ArrayList<>();
        for (OrdSystemCustomer customer : resultList) {
            OrdSystemCustomerRefAe systemCustomerRefAe = new OrdSystemCustomerRefAe();
            systemCustomerRefAe.setAccEntityId(customer.getAccEntityId());
            systemCustomerRefAe.setCustomerId(customer.getCustomerId());
            systemCustomerRefAe.setEnabledFlag(customer.getEnabledFlag());
            systemCustomerRefAe.set__status(customer.get__status());
            customerRefAeService.submitByCustomer(request, systemCustomerRefAe);
        }
    }
}
