package com.hand.hec.exp.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.exp.dto.ExpMoRepTypeRefPayUd;

import java.util.List;

/**
 * <p>
 * IExpMoRepTypeRefPayUdService
 * </p>
 *
 * @author yang.duan 2019/01/10 14:56
 */
public interface IExpMoRepTypeRefPayUdService extends IBaseService<ExpMoRepTypeRefPayUd>, ProxySelf<IExpMoRepTypeRefPayUdService>{
    /**
     * <p>报销单类型-付款用途批量提交<p/>
     * @param IRequest request
     * @param List<ExpMoRepTypeRefPayUd> payUds
     * @return List<ExpMoRepTypeRefPayUd>
     * @throws RuntimeException 运行时异常
     * @author yang.duan 2019/2/26 14:22
     */
    List<ExpMoRepTypeRefPayUd> batchSubmit(IRequest request,List<ExpMoRepTypeRefPayUd> payUds) throws RuntimeException;
}