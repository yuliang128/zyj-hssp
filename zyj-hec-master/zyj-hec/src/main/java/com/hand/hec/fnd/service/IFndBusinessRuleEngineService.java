package com.hand.hec.fnd.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hec.fnd.dto.FndBusinessRule;
import com.hand.hec.fnd.dto.FndBusinessRuleDetail;
import com.hand.hec.fnd.dto.FndBusinessRuleParameter;

import java.util.HashMap;
import java.util.List;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: MouseZhou
 * \* Date: 2018/2/23
 * \* Time: 13:45
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */

public interface IFndBusinessRuleEngineService extends ProxySelf<IFndBusinessRuleEngineService> {

    public List<HashMap> getSqlParameter(IRequest context, FndBusinessRule businessRule, List<FndBusinessRuleDetail> ruleDetailList, String docCategory, String docId);

    public List<FndBusinessRuleDetail> getBusinessRuleDetailList(IRequest context, FndBusinessRule businessRule);

    public HashMap<Long,FndBusinessRuleParameter> getBusinessRuleParameterMap(IRequest context, List<FndBusinessRuleDetail> businessRuleDetailList);

    public boolean validateSingleDetail(IRequest context, FndBusinessRuleDetail businessRuleDetail, List<HashMap> conditionValueList, String dataType) ;

    public boolean validateBusinessRule(IRequest context, FndBusinessRule businessRule, String docCategory, String docId);

    public boolean validateBusinessRule(IRequest context, Long businessRuleId, String docCategory, String docId);
}