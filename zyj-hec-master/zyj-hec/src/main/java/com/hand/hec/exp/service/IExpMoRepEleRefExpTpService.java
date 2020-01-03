package com.hand.hec.exp.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.exp.dto.ExpMoRepEleRefExpTp;

import java.util.List;

/**
 * <p>
 * IExpMoRepEleRefExpTpService
 * </p>
 *
 * @author yang.duan 2019/01/10 14:52
 */
public interface IExpMoRepEleRefExpTpService extends IBaseService<ExpMoRepEleRefExpTp>, ProxySelf<IExpMoRepEleRefExpTpService>{
    /**
     * <p>报销单类型定义-页面元素-报销类型批量提交<p/>
     * @param List<ExpMoRepEleRefExpTp> refExpTps
     * @param IRequest request
     * @return List<ExpMoRepEleRefExpTp>
     * @throws RuntimeException exception description
     * @author yang.duan 2019/2/27 13:46
     */
    List<ExpMoRepEleRefExpTp> batchSubmit(IRequest request,List<ExpMoRepEleRefExpTp> refExpTps) throws RuntimeException;
}