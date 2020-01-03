package com.hand.hec.csh.service.impl;

import com.hand.hap.core.BaseConstants;
import com.hand.hap.core.IRequest;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.Comparison;
import com.hand.hap.mybatis.common.query.WhereField;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.hand.hec.csh.dto.CshPaymentBatchTpDtl;
import com.hand.hec.csh.service.ICshPaymentBatchTpDtlService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class CshPaymentBatchTpDtlServiceImpl extends BaseServiceImpl<CshPaymentBatchTpDtl>
                implements ICshPaymentBatchTpDtlService {


    /**
     * 获取付款批类型中的组批规则
     *
     * @Author Tagin
     * @Date 2019-03-19 12:35
     * @param iRequest 请求
     * @param typeId 付款批类型ID
     * @Return java.util.List<com.hand.hec.csh.dto.CshPaymentBatchTpDtl>
     * @Version 1.0
     **/
    @Override
    public List<CshPaymentBatchTpDtl> queryCondition(IRequest iRequest, Long typeId) {
        CshPaymentBatchTpDtl dto = new CshPaymentBatchTpDtl();
        dto.setTypeId(typeId);
        dto.setEnabledFlag(BaseConstants.YES);
        Criteria criteria = new Criteria(dto);
        criteria.where(new WhereField(CshPaymentBatchTpDtl.FIELD_TYPE_ID, Comparison.EQUAL));
        criteria.where(new WhereField(CshPaymentBatchTpDtl.FIELD_ENABLED_FLAG, Comparison.EQUAL));
        return self().selectOptions(iRequest, dto, criteria);
    }
}
