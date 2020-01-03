package com.hand.hec.csh.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hand.hec.csh.dto.CshPaymentRuleParameter;
import com.hand.hec.csh.exception.CshPaymentRuleParameterException;
import com.hand.hec.csh.mapper.CshPaymentRuleParameterMapper;
import com.hand.hec.csh.service.ICshPaymentRuleParameterService;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.hand.hec.csh.exception.CshPaymentRuleParameterException.PAYMENT_RULE_PARAMETER_SQL;

@Service
@Transactional(rollbackFor = Exception.class)
public class CshPaymentRuleParameterServiceImpl extends BaseServiceImpl<CshPaymentRuleParameter> implements ICshPaymentRuleParameterService{

    @Autowired
    CshPaymentRuleParameterMapper cshPaymentRuleParameterMapper;

    @Override
    public List<CshPaymentRuleParameter> checkSql(IRequest request, String sqlContents) throws CshPaymentRuleParameterException {
        if(!sqlContents.isEmpty()){
            sqlContents = sqlContents.replace("p_csh_doc_header_id","0");
            sqlContents = sqlContents.replace("p_sql_param_1","0");
            sqlContents = sqlContents.replace("p_sql_param_2","0");
            sqlContents = sqlContents.replace("p_sql_param_3","0");
            sqlContents = sqlContents.replace("p_sql_param_4","0");
        }

        try{
            cshPaymentRuleParameterMapper.checkSql(sqlContents);
        }catch (Exception e){
            throw new CshPaymentRuleParameterException(PAYMENT_RULE_PARAMETER_SQL,PAYMENT_RULE_PARAMETER_SQL,null);
        }

        //若sql运行无异常，则resultFlag返回Y
        CshPaymentRuleParameter cshPaymentRuleParameter = CshPaymentRuleParameter.builder().resultFlag("Y").build();
        List<CshPaymentRuleParameter> list = new ArrayList<>();
        list.add(cshPaymentRuleParameter);
        return list;
    }
}