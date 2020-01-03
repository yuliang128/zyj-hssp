package com.hand.hec.fnd.service.impl;

import com.hand.hap.code.rule.exception.CodeRuleException;
import com.hand.hap.code.rule.service.ISysCodeRuleProcessService;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.fnd.dto.FndCodingRule;
import com.hand.hec.fnd.mapper.FndCodingRuleMapper;
import com.hand.hec.fnd.mapper.FndCodingRuleObjectMapper;
import com.hand.hec.fnd.service.IFndCodingRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hand.hec.fnd.dto.FndCodingRuleObject;
import com.hand.hec.fnd.service.IFndCodingRuleObjectService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class FndCodingRuleObjectServiceImpl extends BaseServiceImpl<FndCodingRuleObject>
                implements IFndCodingRuleObjectService {


    @Autowired
    FndCodingRuleMapper codingRuleMapper;

    @Autowired
    IFndCodingRuleService ruleService;

    @Autowired
    FndCodingRuleObjectMapper mapper;

    @Autowired
    ISysCodeRuleProcessService codeRuleProcessService;


    /**
     * 删除编码规则对象之前先删除编码规则
     * 
     * @param dto
     * @author zhongyu
     * @return
     */
    @Override
    public int deleteByPrimaryKey(FndCodingRuleObject dto) {
        FndCodingRule fndCodingRule = new FndCodingRule();
        fndCodingRule.setCodingRuleObjectId(dto.getCodingRuleObjectId());
        List<FndCodingRule> ruleList = codingRuleMapper.select(fndCodingRule);
        if (ruleList != null && ruleList.size() > 0) {
            for (FndCodingRule rule : ruleList) {
                ruleService.deleteByPrimaryKey(rule);
            }
        }
        return super.deleteByPrimaryKey(dto);
    }

    @Override
    public String getRuleCode(String docCategory, String docType, Long magOrgId, Long companyId, Long accEntityId) {
        List<FndCodingRuleObject> objList =
                        mapper.getMatchCodingRuleObject(docCategory, docType, magOrgId, companyId, accEntityId);

        if (objList == null || objList.size() == 0) {
            return null;
        }

        FndCodingRuleObject obj = objList.get(0);
        String code = null;

        try {
            code = codeRuleProcessService.getRuleCode(obj.getCodeRuleCode());
        } catch (CodeRuleException e) {
            e.printStackTrace();
        }

        return code;
    }
}
