package com.hand.hec.exp.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.exp.dto.ExpMoObjectValue;
import com.hand.hec.exp.dto.ExpMoReqEleRefLnObj;
import com.hand.hec.exp.mapper.ExpMoReqEleRefLnObjMapper;
import com.hand.hec.exp.mapper.ExpMoReqTypeRefHdObjMapper;
import com.hand.hec.exp.service.IExpMoReqEleRefLnObjService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xuzheng.jiang@hand-china.com
 * @date 2019/2/28 16:38
 * Description:申请单类型分配页面元素行费用对象ServiceImpl
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ExpMoReqEleRefLnObjServiceImpl extends BaseServiceImpl<ExpMoReqEleRefLnObj> implements IExpMoReqEleRefLnObjService {
    @Autowired
    ExpMoReqEleRefLnObjMapper expMoReqEleRefLnObjMapper;

    @Autowired
    ExpMoReqTypeRefHdObjMapper expMoReqTypeRefHdObjMapper;

    /**
     * 查询费用对象信息
     *
     * @param expMoReqEleRefLnObj 申请单费用对象类型
     * @param request
     * @param pageNum
     * @param pageSize
     * @param criteria
     * @return 返回申请单页面元素分配的费用对象信息
     * @author jiangxz 2019-02-25 14:43
     */
    @Override
    public List<ExpMoReqEleRefLnObj> queryAllInformation(IRequest request, ExpMoReqEleRefLnObj expMoReqEleRefLnObj, Criteria criteria, Integer pageNum, Integer pageSize) {
        List<ExpMoReqEleRefLnObj> expMoReqEleRefLnObjList = new ArrayList<>();
        List<ExpMoReqEleRefLnObj> aExpMoReqEleRefLnObjList = new ArrayList<>();
        expMoReqEleRefLnObjList = this.selectOptions(request, expMoReqEleRefLnObj, criteria, pageNum, pageSize);
        for (ExpMoReqEleRefLnObj expLnObj : expMoReqEleRefLnObjList) {
            ExpMoObjectValue expMoObjectValue = new ExpMoObjectValue();
            if (("VALUE_LIST").equals(expLnObj.getExpenseObjectMethod())) {
                expMoObjectValue = expMoReqTypeRefHdObjMapper.queryObjectValue(expLnObj.getMoExpObjTypeId(), expLnObj.getDefaultMoObjectId());
            } else if (("SQL_TEXT").equals(expLnObj.getExpenseObjectMethod())) {
                expMoObjectValue = expMoReqTypeRefHdObjMapper.queryObjectSearch(expLnObj.getSqlQuery(), expLnObj.getDefaultMoObjectId());
            }
            expLnObj.setDefaultMoObjectCode(expMoObjectValue.getCode());
            expLnObj.setDefaultMoObjectName(expMoObjectValue.getName());
            aExpMoReqEleRefLnObjList.add(expLnObj);
        }
        return aExpMoReqEleRefLnObjList;
    }
}