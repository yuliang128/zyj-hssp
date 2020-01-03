package com.hand.hec.exp.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.exp.dto.ExpMoReqItem;
import com.hand.hec.exp.exception.ReqItemMultiException;

import java.util.List;

public interface IExpMoReqItemService extends IBaseService<ExpMoReqItem>, ProxySelf<IExpMoReqItemService>{
    /**
     * <p>申请项目批量提交<p/>
     * @param request
     * @param expMoReqItems
     * @return List<ExpMoReqItem>
     * @throws ReqItemMultiException exception 申请项目代码重复定义异常
     * @author yang.duan 2019/2/20 9:40
     */
    List<ExpMoReqItem> batchSubmit(IRequest request,List<ExpMoReqItem> expMoReqItems) throws ReqItemMultiException;

    /**
     * <p>根据报销类型获取费用项目</p>
     *
     * @param request
     * @param moExpenseTypeId
     * @return List<ExpMoReqItem>
     * @author yang.duan 2019/4/29 11:30
     **/
    List<ExpMoReqItem> getReqItemByExpenseType(IRequest request,Long moExpenseTypeId);
}