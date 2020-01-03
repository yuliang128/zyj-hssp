package com.hand.hec.csh.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.csh.dto.CshPaymentEntityRule;
import com.hand.hec.csh.exception.CshPaymentEntityRuleException;

public interface ICshPaymentEntityRuleService
                extends IBaseService<CshPaymentEntityRule>, ProxySelf<ICshPaymentEntityRuleService> {


    /**
     * 插入接口 新增一律使用此接口
     *
     * @param request required
     * @param dto required
     * @author dingwei.ma@hand-china.com 2019-01-23 10:07
     * @return CshPaymentEntityRule
     * @exception CshPaymentEntityRuleException
     */
    CshPaymentEntityRule insertEntityRule(IRequest request, CshPaymentEntityRule dto)
                    throws CshPaymentEntityRuleException;

    /**
     * 更新接口 更新一律使用此接口
     *
     * @param request required dto required
     * @author dingwei.ma@hand-china.com 2019-01-23 10:07
     * @return CshPaymentEntityRule
     */
    CshPaymentEntityRule updateEntityRule(IRequest request, CshPaymentEntityRule dto)
                    throws CshPaymentEntityRuleException;
}
