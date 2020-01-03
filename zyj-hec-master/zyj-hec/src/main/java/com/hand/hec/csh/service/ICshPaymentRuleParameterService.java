package com.hand.hec.csh.service;

import java.util.List;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.csh.dto.CshPaymentRuleParameter;
import com.hand.hec.csh.exception.CshPaymentRuleParameterException;

public interface ICshPaymentRuleParameterService extends IBaseService<CshPaymentRuleParameter>, ProxySelf<ICshPaymentRuleParameterService>{
    
    
    /**
     * 支付权限规则参数代码定义-核对sql是否正确
     *
     * @param request
     * @param sqlContents sql语句
     * @author ngls.luhui 2019-02-27 12:10
     * @return 正确返回true
     */
    List<CshPaymentRuleParameter> checkSql (IRequest request, String sqlContents) throws CshPaymentRuleParameterException;
}