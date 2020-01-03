package com.hand.hec.fnd.service.impl;

import com.hand.hap.core.BaseConstants;
import com.hand.hap.core.IRequest;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.system.dto.DTOStatus;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.fnd.dto.OrdCustomerAccount;
import com.hand.hec.fnd.exception.OrdSystemCustomerException;
import com.hand.hec.fnd.mapper.OrdCustomerAccountRefAeMapper;
import com.hand.hec.fnd.service.IOrdCustomerAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hand.hec.fnd.dto.OrdCustomerAccountRefAe;
import com.hand.hec.fnd.service.IOrdCustomerAccountRefAeService;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * OrdCustomerAccountRefAeServiceImpl
 * </p>
 * 
 * @author guiyuting 2019/02/22 17:01
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class OrdCustomerAccountRefAeServiceImpl extends BaseServiceImpl<OrdCustomerAccountRefAe>
                implements IOrdCustomerAccountRefAeService {

    @Autowired
    private IOrdCustomerAccountService accountService;

    @Autowired
    private OrdCustomerAccountRefAeMapper accountRefAeMapper;


    public OrdCustomerAccountRefAe insertCustomerAccountRefAe(IRequest request,
                    OrdCustomerAccountRefAe customerAccountRefAe) throws OrdSystemCustomerException {
        OrdCustomerAccountRefAe result = new OrdCustomerAccountRefAe();
        checkPrimaryAccount(request, customerAccountRefAe);
        checkAccountAlreadyAssign(customerAccountRefAe);
        result = self().insertSelective(request, customerAccountRefAe);
        return result;
    }

    public OrdCustomerAccountRefAe updateCustomerAccountRefAe(IRequest request,
                    OrdCustomerAccountRefAe customerAccountRefAe) throws OrdSystemCustomerException {
        checkPrimaryAccount(request, customerAccountRefAe);
        self().updateByAccEntity(request, customerAccountRefAe);
        return customerAccountRefAe;
    }

    @Override
    public void updateByAccEntity(IRequest request, OrdCustomerAccountRefAe accountRefAe) {
        accountRefAeMapper.updateByAccEntity(accountRefAe);
    }

    @Override
    public List<OrdCustomerAccountRefAe> submitSysCustomerAccountRefAe(IRequest request,
                    List<OrdCustomerAccountRefAe> customerAccountRefAes) throws OrdSystemCustomerException {
        List<OrdCustomerAccountRefAe> list = new ArrayList<>();
        for (OrdCustomerAccountRefAe accountRefAe : customerAccountRefAes) {
            switch (accountRefAe.get__status()) {
                case DTOStatus.ADD:
                    accountRefAe = this.insertCustomerAccountRefAe(request, accountRefAe);
                    break;
                case DTOStatus.UPDATE:
                    accountRefAe = this.updateCustomerAccountRefAe(request, accountRefAe);
                    break;
                case DTOStatus.DELETE:
                    self().deleteByPrimaryKey(accountRefAe);
                    break;
                default:
                    break;
            }

            list.add(accountRefAe);
        }
        return list;
    }

    /**
     * 校验当前银行账号是否已分配该核算主体
     *
     * @param customerAccountRefAe
     * @author guiyuting 2019-03-04 10:03
     * @return
     */
    private void checkAccountAlreadyAssign(OrdCustomerAccountRefAe customerAccountRefAe)
                    throws OrdSystemCustomerException {
        OrdCustomerAccountRefAe accountRefAe = new OrdCustomerAccountRefAe();
        accountRefAe.setAccEntityId(customerAccountRefAe.getAccEntityId());
        accountRefAe.setAccountId(customerAccountRefAe.getAccountId());

        int num = accountRefAeMapper.selectCount(accountRefAe);
        if (num > 0) {
            throw new OrdSystemCustomerException(OrdSystemCustomerException.BANK_ACC_ENTITY_ERROR, null);
        }
    }

    /**
     * 校验当前核算主体是否已作为同一客户下不同银行账号的主账号
     *
     * @param customerAccountRefAe
     * @author guiyuting 2019-03-04 10:10
     * @return
     */
    private void checkPrimaryAccount(IRequest request, OrdCustomerAccountRefAe customerAccountRefAe)
                    throws OrdSystemCustomerException {
        // 通过银行账号获取到当前客户id
        OrdCustomerAccount account = new OrdCustomerAccount();
        account.setAccountId(customerAccountRefAe.getAccountId());
        account = accountService.selectByPrimaryKey(request, account);

        if (BaseConstants.YES.equals(customerAccountRefAe.getPrimaryFlag())) {
            int primaryNum = accountRefAeMapper.checkPrimaryOnly(customerAccountRefAe.getAccEntityId(),
                            customerAccountRefAe.getAccountId(), account.getCustomerId());
            if (primaryNum > 0) {
                throw new OrdSystemCustomerException(OrdSystemCustomerException.PRIMARY_FLAG_ERROR, null);
            }
        }
    }
}
