package com.hand.hec.fnd.service.impl;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.DTOStatus;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.fnd.dto.OrdCustomerAccountRefAe;
import com.hand.hec.fnd.exception.OrdSystemCustomerException;
import com.hand.hec.fnd.mapper.OrdCustomerAccountMapper;
import com.hand.hec.fnd.service.IOrdCustomerAccountRefAeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hand.hec.fnd.dto.OrdCustomerAccount;
import com.hand.hec.fnd.service.IOrdCustomerAccountService;
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
public class OrdCustomerAccountServiceImpl extends BaseServiceImpl<OrdCustomerAccount>
                implements IOrdCustomerAccountService {

    @Autowired
    private OrdCustomerAccountMapper accountMapper;

    @Autowired
    private IOrdCustomerAccountRefAeService accountRefAeService;

    @Override
    public List<OrdCustomerAccount> submitByCustomer(IRequest request, List<OrdCustomerAccount> customerAccounts)
                    throws OrdSystemCustomerException {
        List<OrdCustomerAccount> list = new ArrayList<>();
        // 录入核算主体级客户账户
        for (OrdCustomerAccount customerAccount : customerAccounts) {
            OrdCustomerAccount ordCustomerAccount = new OrdCustomerAccount();
            switch (customerAccount.get__status()) {
                case DTOStatus.ADD:
                    ordCustomerAccount = self().insertSelective(request, customerAccount);
                    submitCustomerRefAe(request, ordCustomerAccount);
                    break;
                case DTOStatus.UPDATE:
                    ordCustomerAccount = self().updateByPrimaryKey(request, customerAccount);
                    submitCustomerRefAe(request, customerAccount);
                    break;
                case DTOStatus.DELETE:
                    self().deleteByPrimaryKey(customerAccount);
                    break;
                default:
                    break;
            }
            list.add(ordCustomerAccount);
        }
        return list;
    }

    @Override
    public List<OrdCustomerAccount> queryByAccEntity(IRequest request, OrdCustomerAccount customerAccount, int page,
                    int pageSize) {
        PageHelper.startPage(page, pageSize);
        return accountMapper.queryByAccEntity(customerAccount);
    }

    @Override
    public List<OrdCustomerAccount> queryBySysCustomer(IRequest request, OrdCustomerAccount customerAccount, int page,
                    int pageSize) {
        PageHelper.startPage(page, pageSize);
        return accountMapper.queryBySysCustomer(customerAccount);
    }

    /**
     * 录入核算主体信息
     *
     * @param request
     * @param ordCustomerAccount
     * @author guiyuting 2019-03-04 14:44
     * @return
     */
    private List<OrdCustomerAccountRefAe> submitCustomerRefAe(IRequest request, OrdCustomerAccount ordCustomerAccount)
                    throws OrdSystemCustomerException {
        List<OrdCustomerAccountRefAe> refAeList = new ArrayList<>();
        OrdCustomerAccountRefAe customerAccountRefAe = new OrdCustomerAccountRefAe();
        BeanUtils.copyProperties(ordCustomerAccount, customerAccountRefAe);
        customerAccountRefAe.set__status(ordCustomerAccount.get__status());
        refAeList.add(customerAccountRefAe);
        return accountRefAeService.submitSysCustomerAccountRefAe(request, refAeList);

    }

}
