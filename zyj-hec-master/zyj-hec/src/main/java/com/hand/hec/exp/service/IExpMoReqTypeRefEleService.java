package com.hand.hec.exp.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.exp.dto.ExpMoReqTypeRefEle;

import java.util.List;

public interface IExpMoReqTypeRefEleService extends IBaseService<ExpMoReqTypeRefEle>, ProxySelf<IExpMoReqTypeRefEleService>{

    /**
     * 查询当前申请单类型下页面元素信息
     *
     * @param moExpReqTypeId          申请单类型id
     * @param expRequisitionHeaderId  申请单头id
     * @return 返回对象值
     * @author jiangxz 2019/4/18 10:27
     */
    List<ExpMoReqTypeRefEle> expReqEleQuery(IRequest request, Long moExpReqTypeId, Long expRequisitionHeaderId);
}