package com.hand.hec.exp.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.exp.dto.ExpMoReqItemDesc;

import java.util.List;
/**
 * <p>
 * 申请项目说明Service
 * </p>
 *
 * @author yang.cai 2019/02/27 18:43
 */
public interface IExpMoReqItemDescService extends IBaseService<ExpMoReqItemDesc>, ProxySelf<IExpMoReqItemDescService>{
    /**
     * 获取符合条件的申请项目说明
     * @param request
     * @param expMoReqItemDesc
     * @param pageNum
     * @param pageSize
     * @return 获取符合条件的申请项目说明
     */
    List<ExpMoReqItemDesc> queryAll(IRequest request, ExpMoReqItemDesc expMoReqItemDesc, int pageNum,
                                    int pageSize);
}