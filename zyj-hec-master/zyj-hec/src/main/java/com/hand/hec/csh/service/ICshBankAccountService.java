package com.hand.hec.csh.service;


import java.util.List;
import java.util.Map;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.csh.dto.CshBankAccount;

/**
 * <p>
 * 公司付款账户分配账户Service
 * </p>
 *
 * @author xiuxian.xu 2019-03-15 9:36
 */
public interface ICshBankAccountService extends IBaseService<CshBankAccount>, ProxySelf<ICshBankAccountService> {

    /**
     * 查询指定核算主体的银行账户的相关信息(汇率类型,汇率等)
     *
     * @param accEntityId     核算主体ID
     * @param bankAccountName 账号描述
     * @param bankAccountNum  银行账号
     * @param currencyCode    币种
     * @param request         上下文环境
     * @return java.util.List<com.hand.hec.csh.dto.CshBankAccount>
     * @author ngls.luhui 2019-03-06 16:53
     */
    List<CshBankAccount> queryBankAccounts(Long accEntityId, String bankAccountName, String bankAccountNum, String currencyCode, IRequest request);


    /**
     * <p>获取核算主体下银行信息</p>
     *
     * @param request
     * @param accEntityId 核算主体ID
     * @return List<Map>
     * @author yang.duan 2019/5/30 10:00
     **/
    List<Map> queryBankAccountByAe(IRequest request,Long accEntityId);
}