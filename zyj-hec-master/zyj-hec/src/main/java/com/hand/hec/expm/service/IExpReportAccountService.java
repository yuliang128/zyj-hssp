package com.hand.hec.expm.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.expm.dto.ExpReportAccount;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * IExpReportAccountService
 * </p>
 *
 * @author yang.duan 2019/01/10 15:05
 */
public interface IExpReportAccountService extends IBaseService<ExpReportAccount>, ProxySelf<IExpReportAccountService>{
    /**
     *创建报销单费用凭证
     *@Author hui.zhao01@hand-china.com
     *@Date 2019/2/13 16:05
     *@param iRequest
     *@param expReportHeaderId 报销单头Id
     *@param journalDate 创建凭证日期
     *@param batchId 批次ID
     *@param periodName 期间
     *@param amortizationFlag 摊销标识
     *@Version 1.0
     **/
    void createExpReportAccounts(IRequest iRequest, Long expReportHeaderId, Date journalDate,String periodName,String amortizationFlag);

    /**
     *更新凭证信息
     *
     *@Author hui.zhao01@hand-china.com
     *@Date 2019/3/8 17:08
     *@param iRequest 请求上下文
     *@param expReportAccounts 需更新的财务信息
     *@return List<ExpReportAccount>
     *@Version 1.0
     **/
    List<ExpReportAccount> accountUpdate(IRequest iRequest,List<ExpReportAccount> expReportAccounts);

    /**
     *更新凭证汇率信息
     *
     *@Author hui.zhao01@hand-china.com
     *@Date 2019/3/8 17:08
     *@param iRequest 请求上下文
     *@param expReportHeaderId 需更新的报销单信息
     *@param exchangeRate 汇率
     *@Version 1.0
     **/
    void updateAccountExchangeRate(IRequest iRequest,Long expReportHeaderId,BigDecimal exchangeRate,String functionCurrencyCode);

    /**
     *报销单审核查询凭证信息
     *
     *@Author hui.zhao01@hand-china.com
     *@Date 2019/3/8 17:08
     *@param requestContext 请求上下文
     *@param dto 查询条件
     *@param page
     *@param pageSize
     *@return List<ExpReportAccount>
     *@Version 1.0
     **/
    List<ExpReportAccount> auditAccountQuery(IRequest requestContext, ExpReportAccount dto, int page, int pageSize);
}