package com.hand.hec.csh.service;

import java.util.List;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.csh.dto.CshPaymentMethod;

/**
 * <p>
 * 付款方式 Service 接口
 * </p>
 *
 * @author yang.cai@hand-china.com 2019/02/19 15:21
 */
public interface ICshPaymentMethodService extends IBaseService<CshPaymentMethod>, ProxySelf<ICshPaymentMethodService> {

    /**
     * @Description 根据公司（管理组织）获取对应付款方式
     *
     * @Author Tagin
     * @Date 2019-03-04 20:14
     *
     * @Return List<CshPaymentMethod>
     * @Version 1.0
     **/
    List<CshPaymentMethod> queryPaymentMethod(IRequest iRequest,Long magOrgId,Long companyId);

    /**
     * @Description 获取网银标志
     *
     * @Author Tagin
     * @Date 2019-03-14 15:10
     * @param paymentMethodId 付款方式ID
     * @Return java.lang.String
     * @Version 1.0
     **/
    String getEBankFlag(Long paymentMethodId);

}
