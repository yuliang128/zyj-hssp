package com.hand.hec.exp.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.exp.dto.ExpMoExpenseObjectType;
import com.hand.hec.exp.dto.ExpMoObjectValue;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author xuzheng.jiang@hand-china.com
 * @date 2019/3/1 10:45 Description:费用对象类型定义Service
 */
public interface IExpMoExpenseObjectTypeService
                extends IBaseService<ExpMoExpenseObjectType>, ProxySelf<IExpMoExpenseObjectTypeService> {
    /**
     * 根据费用对象ID获取费用对象
     *
     * @param moExpObjTypeId
     * @author mouse 2019-03-22 10:37
     * @return com.hand.hec.exp.dto.ExpMoExpenseObjectType
     */
    ExpMoExpenseObjectType getExpMoExpenseObjectType(IRequest request, Long moExpObjTypeId);

    /**
     * 查询费用对象的LOV
     *
     * @param request
     * @param objectType
     * @param code
     * @param name
     * @author mouse 2019-03-22 14:49
     * @return java.util.List<java.util.Map>
     */
    List<Map> queryMoExpObjValueLov(IRequest request, ExpMoExpenseObjectType objectType, String code, String name);

    /**
     * 校验费用对象sql
     *
     * @param expMoExpenseObjectType 费用对象类型数据
     * @param request 请求数据
     * @return 校验费用对象sql
     * @author jiangxz 2019/2/21 14:27
     */
    List<ExpMoObjectValue> sqlValidate(ExpMoExpenseObjectType expMoExpenseObjectType, IRequest request);
}
