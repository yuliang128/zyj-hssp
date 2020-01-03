package com.hand.hec.csh.service.impl;

import java.util.List;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.Comparison;
import com.hand.hap.mybatis.common.query.WhereField;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.csh.dto.CshMoPaymentReqType;
import com.hand.hec.csh.mapper.CshMoPayReqAsgnComMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hand.hec.csh.dto.CshMoPayReqAsgnCom;
import com.hand.hec.csh.service.ICshMoPayReqAsgnComService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class CshMoPayReqAsgnComServiceImpl extends BaseServiceImpl<CshMoPayReqAsgnCom> implements ICshMoPayReqAsgnComService {
    @Autowired
    CshMoPayReqAsgnComMapper mapper;

    @Override
    public List<CshMoPayReqAsgnCom> queryByTypeId(IRequest request, CshMoPayReqAsgnCom condition, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return mapper.queryByTypeId(condition);
    }

    @Override
    public List<CshMoPayReqAsgnCom> queryComByTypeId(IRequest request, Long moPaymentReqTypeId, Long magOrgId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return mapper.queryComByTypeId(moPaymentReqTypeId,magOrgId);
    }

    @Override
    @Transactional
    public List<CshMoPayReqAsgnCom> batchAssignCom(IRequest request, List<CshMoPayReqAsgnCom> list) {
        for (CshMoPayReqAsgnCom moPayReqAsgnCom : list){
            List<CshMoPaymentReqType> paymentReqTypeList = moPayReqAsgnCom.getPaymentReqTypeList();
            for (CshMoPaymentReqType type : paymentReqTypeList){
                moPayReqAsgnCom.setMoPaymentReqTypeId(type.getMoPaymentReqTypeId());
                Criteria criteria = new Criteria(moPayReqAsgnCom);
                criteria.where(new WhereField(CshMoPayReqAsgnCom.FIELD_COMPANY_ID,Comparison.EQUAL)
                        ,new WhereField(CshMoPayReqAsgnCom.FIELD_MO_PAYMENT_REQ_TYPE_ID,Comparison.EQUAL));
                List<CshMoPayReqAsgnCom> moPayReqAsgnComList = self().selectOptions(request,moPayReqAsgnCom,criteria);
                if (moPayReqAsgnComList.isEmpty()){
                    self().insertSelective(request,moPayReqAsgnCom);
                }
            }

        }
        return list;
    }
}