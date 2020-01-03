package com.hand.hec.exp.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.exp.dto.ExpMoReqTypeRefEle;
import com.hand.hec.exp.mapper.ExpMoReqTypeRefEleMapper;
import com.hand.hec.exp.service.IExpMoReqTypeRefEleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class ExpMoReqTypeRefEleServiceImpl extends BaseServiceImpl<ExpMoReqTypeRefEle> implements IExpMoReqTypeRefEleService{

    @Autowired
    private ExpMoReqTypeRefEleMapper mapper;
    /**
     * 查询当前申请单类型下页面元素信息
     *
     * @param moExpReqTypeId          申请单类型id
     * @param expRequisitionHeaderId  申请单头id
     * @return 返回对象值
     * @author jiangxz 2019/4/18 10:27
     */
    @Override
    public List<ExpMoReqTypeRefEle> expReqEleQuery(IRequest request,Long moExpReqTypeId, Long expRequisitionHeaderId){
        return mapper.expReqEleQuery(moExpReqTypeId, expRequisitionHeaderId);
    }
}