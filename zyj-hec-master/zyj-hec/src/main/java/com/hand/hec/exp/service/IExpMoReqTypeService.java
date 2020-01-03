package com.hand.hec.exp.service;
/**
 * @author xuzheng.jiang@hand-china.com
 * @date 2019/3/25 14:17
 * Description:申请单类型IExpMoReqTypeService
 */

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.exp.dto.ExpMoReqType;
import com.hand.hec.exp.dto.ExpReqTypeChoiceCurrentInfor;

import java.util.List;

public interface IExpMoReqTypeService extends IBaseService<ExpMoReqType>, ProxySelf<IExpMoReqTypeService> {

    /**
     * 查询费用对象信息
     *
     * @param request
     * @return 返回申请单类型列表
     * @author jiangxz 2019-02-25 14:43
     */
    List<ExpMoReqType> queryChoiceRequisitionTypeInfor(IRequest request);


    /**
     * 查询选择类型当前用户信息
     *
     * @param request
     * @param employeeId 员工id
     * @return 返回当前用户信息列表
     * @author jiangxz 2019-02-25 14:43
     */
    List<ExpReqTypeChoiceCurrentInfor> queryExpReqTypeChoiceCurrentInfor(Long employeeId, IRequest request);
}