package com.hand.hec.csh.service.impl;

import com.hand.hap.core.BaseConstants;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hand.hec.csh.dto.CshPaymentMethod;
import com.hand.hec.csh.mapper.CshPaymentMethodMapper;
import com.hand.hec.csh.service.ICshPaymentMethodService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 付款方式 Service 实现类
 * </p>
 *
 * @author yang.cai@hand-china.com 2019/02/19 15:21
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class CshPaymentMethodServiceImpl extends BaseServiceImpl<CshPaymentMethod> implements ICshPaymentMethodService {

    @Autowired
    private CshPaymentMethodMapper cshPaymentMethodMapper;

    /**
     * @Description 根据公司（管理组织）获取对应付款方式
     *
     * @Author Tagin
     * @Date 2019-03-04 20:14
     *
     * @Return List<CshPaymentMethod>
     * @Version 1.0
     **/
    @Override
    public List<CshPaymentMethod> queryPaymentMethod(IRequest iRequest,Long magOrgId,Long companyId) {
        return cshPaymentMethodMapper.queryPaymentMethods(magOrgId,companyId);
    }

    /**
     * @Description 获取网银标志
     *
     * @Author Tagin
     * @Date 2019-03-14 15:10
     * @param paymentMethodId 付款方式ID
     * @Return java.lang.String
     * @Version 1.0
     **/
    @Override
    public String getEBankFlag(Long paymentMethodId) {
        CshPaymentMethod cshPaymentMethod = cshPaymentMethodMapper.selectByPrimaryKey(paymentMethodId);
        if (CshPaymentMethod.PAY_CODE_METHOD_10.equals(cshPaymentMethod.getPayMethodCode())) {
            return BaseConstants.YES;
        } else {
            return BaseConstants.NO;
        }
    }
}
