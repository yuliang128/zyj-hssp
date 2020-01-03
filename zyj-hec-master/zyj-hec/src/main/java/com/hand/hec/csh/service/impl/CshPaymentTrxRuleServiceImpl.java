package com.hand.hec.csh.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.csh.dto.CshPaymentTrxRule;
import com.hand.hec.csh.dto.CshPaymentTrxRuleBiz;
import com.hand.hec.csh.dto.CshPaymentTrxRuleBizDtl;
import com.hand.hec.csh.dto.CshPaymentTrxRuleDtl;
import com.hand.hec.csh.mapper.CshPaymentTrxRuleBizDtlMapper;
import com.hand.hec.csh.mapper.CshPaymentTrxRuleBizMapper;
import com.hand.hec.csh.service.ICshPaymentTrxRuleDtlService;
import com.hand.hec.csh.service.ICshPaymentTrxRuleService;

@Service
@Transactional(rollbackFor = Exception.class)
public class CshPaymentTrxRuleServiceImpl extends BaseServiceImpl<CshPaymentTrxRule>
                implements ICshPaymentTrxRuleService {

    @Autowired
    private CshPaymentTrxRuleBizMapper cshPaymentTrxRuleBizMapper;

    @Autowired
    private CshPaymentTrxRuleBizDtlMapper cshPaymentTrxRuleBizDtlMapper;

    @Autowired
    private ICshPaymentTrxRuleDtlService cshPaymentTrxRuleDtlService;

    /**
     * 判断参数是否满足上下限
     *
     * @Author Tagin
     * @Date 2019-03-13 16:03
     * @param paramCode 参数代码
     * @param lowerLimit 下限值
     * @param upperLimit 上限值
     * @param filtrateMethod 取值方式
     * @Return boolean
     * @Version 1.0
     **/
    public boolean isParameter(String paramCode, String lowerLimit, String upperLimit, String filtrateMethod) {
        try {
            if (filtrateMethod.equals(CshPaymentTrxRule.FILTRATE_METHOD_INCLUDE)) {
                if (paramCode.compareTo(lowerLimit) >= 0 && paramCode.compareTo(upperLimit) <= 0) {
                    return true;
                }
            } else if (filtrateMethod.equals(CshPaymentTrxRule.FILTRATE_METHOD_EXCLUDE)) {
                if (paramCode.compareTo(upperLimit) > 0 || paramCode.compareTo(lowerLimit) < 0) {
                    return true;
                }
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    /**
     * 判断函数是否满足
     *
     * @Author Tagin
     * @Date 2019-03-13 16:47
     * @param functionName 函数名称 返回类型为 Boolean 类型
     * @param parameterValue 函数参数
     * @Return boolean
     * @Version 1.0
     **/
    public boolean isFunction(String functionName, String parameterValue) {
        try {
            if (functionName != null && !functionName.isEmpty()) {
                return (Boolean) this.getClass().getMethod(functionName, new Class[] {String.class}).invoke(this,
                                new Object[] {parameterValue});
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    /**
     * 获取付款事务生成规则
     *
     * @Author Tagin
     * @Date 2019-03-13 21:27
     * @param magOrgId 管理组织ID
     * @param accEntityId 核算主体ID
     * @param documentCategory 单据类别
     * @param documentType 单据类型代码
     * @param payeeCategory 收款方类别
     * @param payeeCode 收款方
     * @param accountNum 付款账户
     * @param paymentCurrency 付款币种
     * @param paymentMethod 付款方式
     * @param paymentUsede 付款用途
     * @Return java.lang.Long
     * @Version 1.0
     **/
    @Override
    public Long getTrxRuleId(Long magOrgId, Long accEntityId, String documentCategory, String documentType,
                    String payeeCategory, String payeeCode, String accountNum, String paymentCurrency,
                    String paymentMethod, String paymentUsede) {
        Boolean isMatch = false;
        // 获取业务规则
        List<CshPaymentTrxRuleBiz> cshPaymentTrxRuleBizs =
                        cshPaymentTrxRuleBizMapper.getTrxRuleBiz(magOrgId, documentCategory, accEntityId);
        if (CollectionUtils.isNotEmpty(cshPaymentTrxRuleBizs)) {
            for (CshPaymentTrxRuleBiz biz : cshPaymentTrxRuleBizs) {
                // 参数
                if (CshPaymentTrxRuleBiz.TYPE_CODE_10.equals(biz.getTypeCode())) {
                    // 获取业务规则明细
                    List<CshPaymentTrxRuleBizDtl> cshPaymentTrxRuleBizDtls =
                                    cshPaymentTrxRuleBizDtlMapper.getTrxRuleBizDtl(biz.getRuleBizId());
                    for (CshPaymentTrxRuleBizDtl dtl : cshPaymentTrxRuleBizDtls) {
                        String paramCode = null;
                        if (CshPaymentTrxRuleBizDtl.PARAMETER_DOCUMENT_CATEGORY.equals(dtl.getRuleParameter())) {
                            paramCode = documentCategory;
                        } else if (CshPaymentTrxRuleBizDtl.PARAMETER_DOCUMENT_TYPE.equals(dtl.getRuleParameter())) {
                            paramCode = documentType;
                        } else if (CshPaymentTrxRuleBizDtl.PARAMETER_PAYEE_CATEGORY.equals(dtl.getRuleParameter())) {
                            paramCode = payeeCategory;
                        } else if (CshPaymentTrxRuleBizDtl.PARAMETER_PAYEE.equals(dtl.getRuleParameter())) {
                            paramCode = payeeCode;
                        } else if (CshPaymentTrxRuleBizDtl.PARAMETER_PAYMENT_ACCOUNT.equals(dtl.getRuleParameter())) {
                            paramCode = accountNum;
                        } else if (CshPaymentTrxRuleBizDtl.PARAMETER_PAYMENT_CURRENCY.equals(dtl.getRuleParameter())) {
                            paramCode = paymentCurrency;
                        } else if (CshPaymentTrxRuleBizDtl.PARAMETER_PAYMENT_METHOD.equals(dtl.getRuleParameter())) {
                            paramCode = paymentMethod;
                        } else if (CshPaymentTrxRuleBizDtl.PARAMETER_PAYMENT_USEDE.equals(dtl.getRuleParameter())) {
                            paramCode = paymentUsede;
                        }
                        isMatch = isParameter(paramCode, dtl.getLowerLimit(), dtl.getUpperLimit(),
                                        dtl.getFiltrateMethod());
                        if (!isMatch) {
                            break;
                        }
                    }
                    if (isMatch) {
                        return biz.getRuleId();
                    }
                }
                // 函数
                if (CshPaymentTrxRuleBiz.TYPE_CODE_20.equals(biz.getTypeCode())) {
                    isMatch = isFunction(biz.getCustomFunction(), "EXP_REPORT");
                    if (isMatch) {
                        return biz.getRuleId();
                    }
                }
            }
        }
        return null;
    }

    @Override
    public Boolean submitTrx(List<CshPaymentTrxRule> cshPaymentTrxRuleList, IRequest request) {
        // 1.0存储付款事务生成规则
        this.batchUpdate(request, cshPaymentTrxRuleList);

        // 2.存储六条初始预置分组规则
        String[] groupCondition = {"PAYEE_CATEGORY", "PAYEE_ID", "ACCOUNT_NAME", "ACCOUNT_NUMBER", "PAYMENT_METHOD_ID",
                "CURRENCY_CODE"};
        for (CshPaymentTrxRule cshPaymentTrxRule : cshPaymentTrxRuleList) {
            if ("add".equals(cshPaymentTrxRule.get__status())) {
                List<CshPaymentTrxRuleDtl> cshPaymentTrxRuleDtlList = new ArrayList<>();
                // 如果该条事务规则是新增的，那么新增对应的预置六条分组规则
                for (int i = 0; i < 6; i++) {
                    CshPaymentTrxRuleDtl cshPaymentTrxRuleDtl = new CshPaymentTrxRuleDtl();
                    cshPaymentTrxRuleDtl.setRuleId(cshPaymentTrxRule.getRuleId());
                    cshPaymentTrxRuleDtl.setEnabledFlag("Y");
                    cshPaymentTrxRuleDtl.setSystemFlag("Y");
                    cshPaymentTrxRuleDtl.setGroupCondition(groupCondition[i]);
                    cshPaymentTrxRuleDtl.set__status("add");
                    cshPaymentTrxRuleDtlList.add(cshPaymentTrxRuleDtl);
                }
                cshPaymentTrxRuleDtlService.batchUpdate(request, cshPaymentTrxRuleDtlList);
            }
        }
        return true;
    }
}
