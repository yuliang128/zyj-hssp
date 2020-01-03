package com.hand.hec.ssc.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.util.DateUtils;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.fnd.dto.FndBusinessRule;
import com.hand.hec.fnd.service.IFndBusinessRuleEngineService;
import com.hand.hec.fnd.service.IFndBusinessRuleService;
import com.hand.hec.ssc.dto.SscNodeAssignRule;
import com.hand.hec.ssc.dto.SscTaskPool;
import com.hand.hec.ssc.mapper.SscNodeAssignRuleMapper;
import com.hand.hec.ssc.service.ISscNodeAssignRuleService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class SscNodeAssignRuleServiceImpl extends BaseServiceImpl<SscNodeAssignRule> implements ISscNodeAssignRuleService{

	@Autowired
	private SscNodeAssignRuleMapper mapper;

	@Autowired
	private IFndBusinessRuleEngineService fndBusinessRuleEngineService;

	@Autowired
	private IFndBusinessRuleService fndBusinessRuleService;

	@Override
	public List<SscNodeAssignRule> querySscNodeAssignRule(@Param("workerAssignId") Long workerAssignId, IRequest iRequest) {
		return mapper.querySscNodeAssignRule(workerAssignId);
	}

    @Override
    public boolean validateNodeWorkerRule(IRequest iRequest, Long workerAssignId, SscTaskPool sscTaskPool) {
		int count = 0;
		boolean flag;
		List<SscNodeAssignRule> sscNodeAssignRules = new ArrayList<>();
		sscNodeAssignRules = mapper.queryValidateNodeWorkerRule(workerAssignId, DateUtils.getCurrentDate());
		if(!sscNodeAssignRules.isEmpty() && sscNodeAssignRules != null){
			for(SscNodeAssignRule sscNodeAssignRule : sscNodeAssignRules) {
				FndBusinessRule fndBusinessRule = new FndBusinessRule();
				fndBusinessRule.setBusinessRuleId(sscNodeAssignRule.getWflBusinessRuleId());
				fndBusinessRule = fndBusinessRuleService.selectByPrimaryKey(iRequest, fndBusinessRule);
				count++;
				flag = fndBusinessRuleEngineService.validateBusinessRule(iRequest, fndBusinessRule,
						sscTaskPool.getDocCategory(), sscTaskPool.getDocId().toString());
				if(flag){
					return true;
				}
			}
		}
		if(count>0){
			return false;
		}
        return true;
    }
}