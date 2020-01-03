package com.hand.hec.csh.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.Comparison;
import com.hand.hap.mybatis.common.query.WhereField;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.csh.dto.CshPaymentEntityRule;
import com.hand.hec.csh.exception.CshPaymentEntityRuleException;
import com.hand.hec.csh.mapper.CshPaymentEntityRuleMapper;
import com.hand.hec.csh.service.ICshPaymentEntityRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class CshPaymentEntityRuleServiceImpl extends BaseServiceImpl<CshPaymentEntityRule>
                implements ICshPaymentEntityRuleService {

    @Autowired
    CshPaymentEntityRuleMapper cshPaymentEntityRuleMapper;

    @Override
    public CshPaymentEntityRule insertEntityRule(IRequest request, CshPaymentEntityRule dto)
                    throws CshPaymentEntityRuleException {
        Criteria criteria = new Criteria(dto);
        criteria.where(new WhereField(CshPaymentEntityRule.FIELD_DOCUMENT_CATEGORY, Comparison.EQUAL),
                        new WhereField(CshPaymentEntityRule.FIELD_PRIORITY, Comparison.EQUAL),
                        new WhereField(CshPaymentEntityRule.FIELD_ACC_ENTITY_ID, Comparison.EQUAL));
        List<CshPaymentEntityRule> cshPaymentEntityRuleList = self().selectOptions(request, dto, criteria);
        if (!cshPaymentEntityRuleList.isEmpty()) {
            throw new CshPaymentEntityRuleException(CshPaymentEntityRuleException.PAYMENT_ENTITY_RULE_ONE_DUPLICATE,
                            CshPaymentEntityRuleException.PAYMENT_ENTITY_RULE_ONE_DUPLICATE, null);
        }
        self().insertSelective(request, dto);
        return dto;
    }

    @Override
    public CshPaymentEntityRule updateEntityRule(IRequest request, CshPaymentEntityRule dto)
                    throws CshPaymentEntityRuleException {
        self().updateByPrimaryKey(request, dto);

        Criteria criteria = new Criteria(dto);
        criteria.where(new WhereField(CshPaymentEntityRule.FIELD_DOCUMENT_CATEGORY, Comparison.EQUAL),
                        new WhereField(CshPaymentEntityRule.FIELD_PRIORITY, Comparison.EQUAL),
                        new WhereField(CshPaymentEntityRule.FIELD_ACC_ENTITY_ID, Comparison.EQUAL));
        List<CshPaymentEntityRule> cshPaymentEntityRuleList = self().selectOptions(request, dto, criteria);
        if (!cshPaymentEntityRuleList.isEmpty()) {
            throw new CshPaymentEntityRuleException(CshPaymentEntityRuleException.PAYMENT_ENTITY_RULE_ONE_DUPLICATE,
                            CshPaymentEntityRuleException.PAYMENT_ENTITY_RULE_ONE_DUPLICATE, null);
        }
        return dto;
    }
}
