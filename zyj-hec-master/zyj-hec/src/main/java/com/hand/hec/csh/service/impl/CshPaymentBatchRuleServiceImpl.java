package com.hand.hec.csh.service.impl;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hand.hap.core.BaseConstants;
import com.hand.hap.sys.constants.ParameterConstants;
import com.hand.hap.sys.service.ISysParameterService;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.csh.dto.CshPaymentBatchRule;
import com.hand.hec.csh.dto.CshPaymentBatchRuleDtl;
import com.hand.hec.csh.dto.CshPaymentBatchRuleLn;
import com.hand.hec.csh.mapper.CshPaymentBatchRuleDtlMapper;
import com.hand.hec.csh.mapper.CshPaymentBatchRuleLnMapper;
import com.hand.hec.csh.service.ICshPaymentBatchRuleService;

@Service
@Transactional(rollbackFor = Exception.class)
public class CshPaymentBatchRuleServiceImpl extends BaseServiceImpl<CshPaymentBatchRule>
                implements ICshPaymentBatchRuleService {
    @Autowired
    private CshPaymentBatchRuleLnMapper cshPaymentBatchRuleLnMapper;

    @Autowired
    private CshPaymentBatchRuleDtlMapper cshPaymentBatchRuleDtlMapper;

    @Autowired
    private ISysParameterService sysParameterService;

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
            if (filtrateMethod.equals(CshPaymentBatchRule.FILTRATE_METHOD_INCLUDE)) {
                if (paramCode.compareTo(lowerLimit) >= 0 && paramCode.compareTo(upperLimit) <= 0) {
                    return true;
                }
            } else if (filtrateMethod.equals(CshPaymentBatchRule.FILTRATE_METHOD_EXCLUDE)) {
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
     * 获取付款批类型ID
     *
     * @author Tagin
     * @date 2019-04-03 20:37
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
     * @return java.lang.Long
     * @version 1.0
     **/
    @Override
    public Long getTypeId(Long magOrgId, Long accEntityId, String documentCategory, String documentType,
                    String payeeCategory, String payeeCode, String accountNum, String paymentCurrency,
                    String paymentMethod, String paymentUsede) {
        Boolean isMatch = false;
        Long typeId = null;
        // 付款批决定规则
        List<CshPaymentBatchRuleLn> cshPaymentBatchRuleLns =
                        cshPaymentBatchRuleLnMapper.getBatchRuleLn(magOrgId, accEntityId);
        if (CollectionUtils.isNotEmpty(cshPaymentBatchRuleLns)) {
            for (CshPaymentBatchRuleLn rule : cshPaymentBatchRuleLns) {
                // 参数
                if (CshPaymentBatchRuleLn.TYPE_CODE_10.equals(rule.getTypeCode())) {
                    List<CshPaymentBatchRuleDtl> cshPaymentBatchRuleDtls =
                                    cshPaymentBatchRuleDtlMapper.getBatchRuleDtl(rule.getRuleLnsId());
                    for (CshPaymentBatchRuleDtl dtl : cshPaymentBatchRuleDtls) {
                        String paramCode = null;
                        if (CshPaymentBatchRuleDtl.PARAMETER_DOCUMENT_CATEGORY.equals(dtl.getRuleParameter())) {
                            paramCode = documentCategory;
                        } else if (CshPaymentBatchRuleDtl.PARAMETER_DOCUMENT_TYPE.equals(dtl.getRuleParameter())) {
                            paramCode = documentType;
                        } else if (CshPaymentBatchRuleDtl.PARAMETER_PAYEE_CATEGORY.equals(dtl.getRuleParameter())) {
                            paramCode = payeeCategory;
                        } else if (CshPaymentBatchRuleDtl.PARAMETER_PAYEE.equals(dtl.getRuleParameter())) {
                            paramCode = payeeCode;
                        } else if (CshPaymentBatchRuleDtl.PARAMETER_PAYMENT_ACCOUNT.equals(dtl.getRuleParameter())) {
                            paramCode = accountNum;
                        } else if (CshPaymentBatchRuleDtl.PARAMETER_PAYMENT_CURRENCY.equals(dtl.getRuleParameter())) {
                            paramCode = paymentCurrency;
                        } else if (CshPaymentBatchRuleDtl.PARAMETER_PAYMENT_METHOD.equals(dtl.getRuleParameter())) {
                            paramCode = paymentMethod;
                        } else if (CshPaymentBatchRuleDtl.PARAMETER_PAYMENT_USEDE.equals(dtl.getRuleParameter())) {
                            paramCode = paymentUsede;
                        }
                        isMatch = isParameter(paramCode, dtl.getLowerLimit(), dtl.getUpperLimit(),
                                        dtl.getFiltrateMethod());
                        if (!isMatch) {
                            break;
                        }
                    }
                    if (isMatch) {
                        typeId = rule.getTypeId();
                        break;
                    }
                }
                // 函数
                if (CshPaymentBatchRuleLn.TYPE_CODE_20.equals(rule.getTypeCode())) {
                    isMatch = isFunction(rule.getCustomFunction(), "EXP_REPORT");
                    if (isMatch) {
                        typeId = rule.getTypeId();
                        break;
                    }
                }
            }
        }
        if (typeId == null) {
            String batchTypeParam = sysParameterService.queryParamValueByCode(
                            ParameterConstants.PARAM_CSH_PAYMENT_BATCH_DEFAULT_TYPE, null, null, null, accEntityId,
                            null, magOrgId, null);
            if (!BaseConstants.NO.equals(batchTypeParam)) {
                typeId = Long.valueOf(batchTypeParam);
            }
        }
        return typeId;
    }
}
