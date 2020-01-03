package com.hand.hec.exp.service;

import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.exp.dto.ExpRequisitionObject;

import java.util.List;

public interface IExpRequisitionObjectService extends IBaseService<ExpRequisitionObject>, ProxySelf<IExpRequisitionObjectService> {

    /**
     * <p>
     * 申请单费用对象更新
     * <p/>
     *
     * @param dto 需要更新的申请单费用对象
     * @return 返回null
     * @author jiangxz 2019/4/4 13:26
     */
    ExpRequisitionObject updateExpReqObject(ExpRequisitionObject dto);

    /**
     * <p>
     * 查询申请单头对象
     * <p/>
     *
     * @param moExpReqTypeId 申请单类型ID
     * @return 费用头对象的list
     * @author jiangxz 2019/4/4 10:09
     */
    List<ExpRequisitionObject> queryObjectByReqHd(Long moExpReqTypeId);

    /**
     * <p>
     * 查询申请单行对象
     * <p/>
     *
     * @param moExpReqTypeId     申请单类型ID
     * @param reqPageElementCode 申请单行页面类型代码
     * @return 费用头对象的list
     * @author jiangxz 2019/4/4 10:09
     */
    List<ExpRequisitionObject> queryObjectByReqLn(Long moExpReqTypeId, Long reqPageElementCode);
}