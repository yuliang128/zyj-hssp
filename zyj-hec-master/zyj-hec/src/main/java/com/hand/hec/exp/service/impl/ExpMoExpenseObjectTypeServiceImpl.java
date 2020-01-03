package com.hand.hec.exp.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.exp.dto.ExpMoExpenseObjectType;
import com.hand.hec.exp.dto.ExpMoObjectValue;
import com.hand.hec.exp.mapper.ExpMoExpenseObjectTypeMapper;
import com.hand.hec.exp.mapper.ExpMoReqTypeRefHdObjMapper;
import com.hand.hec.exp.service.IExpMoExpenseObjectTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author xuzheng.jiang@hand-china.com
 * @date 2019/3/1 10:47 Description:费用对象类型定义ServiceImpl
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ExpMoExpenseObjectTypeServiceImpl extends BaseServiceImpl<ExpMoExpenseObjectType>
                implements IExpMoExpenseObjectTypeService {

    @Autowired
    ExpMoReqTypeRefHdObjMapper expMoReqTypeRefHdObjMapper;

    @Autowired
    ExpMoExpenseObjectTypeMapper typeMapper;

    @Override
    public ExpMoExpenseObjectType getExpMoExpenseObjectType(IRequest request, Long moExpObjTypeId) {
        ExpMoExpenseObjectType type = new ExpMoExpenseObjectType();
        type.setMoExpObjTypeId(moExpObjTypeId);
        type = mapper.selectByPrimaryKey(type);
        return type;
    }

    @Override
    public List<Map> queryMoExpObjValueLov(IRequest request, ExpMoExpenseObjectType objectType, String code,
                    String name) {
        return typeMapper.queryMoExpObjValueLov(objectType, code, name);
    }

    /**
     * 校验费用对象sql
     *
     * @param expMoExpenseObjectType 费用对象类型数据
     * @param request 请求数据
     * @return 校验费用对象sql
     * @author jiangxz 2019/2/21 14:27
     */
    @Override
    public List<ExpMoObjectValue> sqlValidate(ExpMoExpenseObjectType expMoExpenseObjectType, IRequest request) {
        List<ExpMoObjectValue> expMoObjectValueList = new ArrayList<>();
        expMoObjectValueList =
                        expMoReqTypeRefHdObjMapper.queryObjectSearchAll(expMoExpenseObjectType.getSqlQuery(), null);
        expMoExpenseObjectType.setSqlValidate(expMoExpenseObjectType.getSqlQuery());
        List<ExpMoExpenseObjectType> expMoExpenseObjectTypeList = new ArrayList<>();
        expMoExpenseObjectTypeList.add(expMoExpenseObjectType);
        expMoExpenseObjectTypeList = this.batchUpdate(request, expMoExpenseObjectTypeList);
        return expMoObjectValueList;
    }
}
