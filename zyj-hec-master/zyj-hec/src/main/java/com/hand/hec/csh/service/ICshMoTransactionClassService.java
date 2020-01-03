package com.hand.hec.csh.service;

import java.util.List;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.csh.dto.CshMoTransactionClass;

/**
 * <p>
 * 现金事物分类定义service接口
 * </p>
 *
 * @author LJK 2019/02/19 12:09
 */
public interface ICshMoTransactionClassService extends IBaseService<CshMoTransactionClass>, ProxySelf<ICshMoTransactionClassService>{

    /**
     * 还款申请单借款行明细-行借款类型查询
     *
     * @param request 上下文
     * @author jialin.xing@hand-china.com 2019-05-29 23:31
     * @return java.util.List<com.hand.hec.csh.dto.CshMoTransactionClass>
     */
    List<CshMoTransactionClass> queryTrxClass(IRequest request);

}