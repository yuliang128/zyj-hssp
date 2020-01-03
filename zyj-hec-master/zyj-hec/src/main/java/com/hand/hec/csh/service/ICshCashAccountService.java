package com.hand.hec.csh.service;

import java.util.List;
import java.util.Map;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.csh.dto.CshCashAccount;

public interface ICshCashAccountService extends IBaseService<CshCashAccount>, ProxySelf<ICshCashAccountService>{
    /**
     *查询账套信息
     *
     * @param request 请求上下文
     * @param setOfBooksId 账套Id
     * @author dingwei.ma@hand-china.com 2019-02-22 11:14
     * @return List<Map>
     */
    List<Map> queryByBooksId(IRequest request, Long setOfBooksId);

    /**
     *查询现金流量表分配科目
     *
     * @param request 请求上下文
     * @param record 现金流量表分配科目实体类
     * @param pageNum 页码
     * @param pageSize 页数
     * @author dingwei.ma@hand-china.com 2019-02-22 11:14
     * @return List<CshCashAccount>
     */
    List<CshCashAccount> queryCashAccount(IRequest request,CshCashAccount record, int pageNum, int pageSize);
}