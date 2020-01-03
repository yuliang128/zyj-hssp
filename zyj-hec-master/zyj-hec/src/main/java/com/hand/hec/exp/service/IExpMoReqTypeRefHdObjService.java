package com.hand.hec.exp.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.exp.dto.ExpMoReqTypeRefHdObj;

import java.util.List;

/**
 * @author xuzheng.jiang@hand-china.com
 * @date 2019/2/28 9:53
 * Description:申请单类型页面元素分配费用对象Service
 */

public interface IExpMoReqTypeRefHdObjService extends IBaseService<ExpMoReqTypeRefHdObj>, ProxySelf<IExpMoReqTypeRefHdObjService> {
    /**
     * 查询费用对象信息
     *
     * @param expMoReqTypeRefHdObj 申请单费用对象类型
     * @param request
     * @param pageNum
     * @param pageSize
     * @param criteria
     * @return 返回申请单页面元素分配的费用对象信息
     * @author jiangxz 2019-02-25 14:43
     */
    List<ExpMoReqTypeRefHdObj> queryAllInformation(IRequest request, ExpMoReqTypeRefHdObj expMoReqTypeRefHdObj, Criteria criteria, Integer pageNum, Integer pageSize);
}