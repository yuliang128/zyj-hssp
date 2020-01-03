package com.hand.hec.csh.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.csh.dto.CshPaymentRuleDetail;

public interface ICshPaymentRuleDetailService extends IBaseService<CshPaymentRuleDetail>, ProxySelf<ICshPaymentRuleDetailService>{

    /**
     *
     * 校验函数
     *
     * 检验传过来的Str是否能在数据执行
     */
    public int check(IRequest request,String str);
}