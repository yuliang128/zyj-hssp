package com.hand.hec.exp.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.exp.dto.ExpMoObjectValue;
import com.hand.hec.exp.dto.ExpMoReqTypeRefHdObj;
import com.hand.hec.exp.mapper.ExpMoReqTypeRefHdObjMapper;
import com.hand.hec.exp.service.IExpMoReqTypeRefHdObjService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xuzheng.jiang@hand-china.com
 * @date 2019/2/28 10:51
 * Description: 申请单类型定义页面元素分配费用对象ServiceImpl
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ExpMoReqTypeRefHdObjServiceImpl extends BaseServiceImpl<ExpMoReqTypeRefHdObj> implements IExpMoReqTypeRefHdObjService {

    @Autowired
    ExpMoReqTypeRefHdObjMapper expMoReqTypeRefHdObjMapper;

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
    @Override
    public List<ExpMoReqTypeRefHdObj> queryAllInformation(IRequest request, ExpMoReqTypeRefHdObj expMoReqTypeRefHdObj, Criteria criteria, Integer pageNum, Integer pageSize) {
        List<ExpMoReqTypeRefHdObj> expMoReqTypeRefHdObjList = new ArrayList<>();
        List<ExpMoReqTypeRefHdObj> aExpMoReqTypeRefHdObjList = new ArrayList<>();
        expMoReqTypeRefHdObjList = this.selectOptions(request, expMoReqTypeRefHdObj, criteria, pageNum, pageSize);
        for (ExpMoReqTypeRefHdObj expHdObj : expMoReqTypeRefHdObjList) {
            ExpMoObjectValue expMoObjectValue = new ExpMoObjectValue();
            if (("VALUE_LIST").equals(expHdObj.getExpenseObjectMethod())) {
                expMoObjectValue = expMoReqTypeRefHdObjMapper.queryObjectValue(expHdObj.getMoExpObjTypeId(), expHdObj.getDefaultMoObjectId());
            } else if (("SQL_TEXT").equals(expHdObj.getExpenseObjectMethod())) {
                expMoObjectValue = expMoReqTypeRefHdObjMapper.queryObjectSearch(expHdObj.getSqlQuery(), expHdObj.getDefaultMoObjectId());
            }
            expHdObj.setDefaultMoObjectCode(expMoObjectValue.getCode());
            expHdObj.setDefaultMoObjectName(expMoObjectValue.getName());
            aExpMoReqTypeRefHdObjList.add(expHdObj);
        }
        return aExpMoReqTypeRefHdObjList;
    }
}