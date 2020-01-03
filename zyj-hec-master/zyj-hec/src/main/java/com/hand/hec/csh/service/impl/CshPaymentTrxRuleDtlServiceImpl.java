package com.hand.hec.csh.service.impl;

import static com.hand.hec.csh.exception.CshPaymentTrxRuleDtlException.PAYMENT_TRX_RULE_UNIQUE;

import java.util.List;

import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hand.hap.core.BaseConstants;
import com.hand.hap.core.IRequest;
import com.hand.hap.core.annotation.StdWho;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.Comparison;
import com.hand.hap.mybatis.common.query.WhereField;
import com.hand.hap.system.dto.BaseDTO;
import com.hand.hap.system.dto.DTOStatus;
import com.hand.hap.system.service.IBaseService;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.csh.dto.CshPaymentTrxRuleDtl;
import com.hand.hec.csh.exception.CshPaymentTrxRuleDtlException;
import com.hand.hec.csh.mapper.CshPaymentTrxRuleDtlMapper;
import com.hand.hec.csh.service.ICshPaymentTrxRuleDtlService;

@Service
@Transactional(rollbackFor = Exception.class)
public class CshPaymentTrxRuleDtlServiceImpl extends BaseServiceImpl<CshPaymentTrxRuleDtl>
                implements ICshPaymentTrxRuleDtlService {

    @Autowired
    CshPaymentTrxRuleDtlMapper cshPaymentTrxRuleDtlMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<CshPaymentTrxRuleDtl> batchSubmit(IRequest request, @StdWho List<CshPaymentTrxRuleDtl> list)
                    throws CshPaymentTrxRuleDtlException {
        IBaseService<CshPaymentTrxRuleDtl> self = ((IBaseService<CshPaymentTrxRuleDtl>) AopContext.currentProxy());
        for (CshPaymentTrxRuleDtl t : list) {
            switch (((BaseDTO) t).get__status()) {
                case DTOStatus.ADD:
                    // 检测该付款规则是否定义过
                    List<CshPaymentTrxRuleDtl> list2 = cshPaymentTrxRuleDtlMapper.select(
                                    CshPaymentTrxRuleDtl.builder().groupCondition(t.getGroupCondition()).build());
                    if (!list2.isEmpty()) {
                        throw new CshPaymentTrxRuleDtlException(PAYMENT_TRX_RULE_UNIQUE, PAYMENT_TRX_RULE_UNIQUE, null);
                    }
                    self.insertSelective(request, t);
                    break;
                case DTOStatus.UPDATE:
                    if (useSelectiveUpdate()) {
                        self.updateByPrimaryKeySelective(request, t);
                    } else {
                        self.updateByPrimaryKey(request, t);
                    }
                    break;
                case DTOStatus.DELETE:
                    self.deleteByPrimaryKey(t);
                    break;
                default:
                    break;
            }
        }
        return list;
    }

    /**
     * 获取规则明细中的分组条件
     *
     * @Author Tagin
     * @Date 2019-03-19 12:35
     * @param iRequest 请求
     * @param ruleId 付款事务生成规则ID
     * @Return java.util.List<com.hand.hec.csh.dto.CshPaymentTrxRuleDtl>
     * @Version 1.0
     **/
    @Override
    public List<CshPaymentTrxRuleDtl> queryCondition(IRequest iRequest, Long ruleId) {
        CshPaymentTrxRuleDtl dto = new CshPaymentTrxRuleDtl();
        dto.setRuleId(ruleId);
        dto.setEnabledFlag(BaseConstants.YES);
        Criteria criteria = new Criteria(dto);
        criteria.where(new WhereField(CshPaymentTrxRuleDtl.FIELD_RULE_ID, Comparison.EQUAL));
        criteria.where(new WhereField(CshPaymentTrxRuleDtl.FIELD_ENABLED_FLAG, Comparison.EQUAL));
        return self().selectOptions(iRequest, dto, criteria);
    }

}
