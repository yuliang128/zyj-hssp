package com.hand.hec.ssc.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.ssc.dto.SscNodeAssignRule;
import com.hand.hec.ssc.dto.SscTaskPool;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ISscNodeAssignRuleService extends IBaseService<SscNodeAssignRule>, ProxySelf<ISscNodeAssignRuleService>{
	/**
	 *工作中心派工规则分配  联动
	 *@Param workerAssignId
	 * @return SscNodeAssignRule
	 * @author bo.zhang 2019-03-20 19:24:36
	 */
	List<SscNodeAssignRule> querySscNodeAssignRule(@Param("workerAssignId") Long workerAssignId, IRequest iRequest);

	/**
	 *验证节点工作人员分派权限
	 *
	 *@Author hui.zhao01@hand-china.com
	 *@Date 2019/3/27 9:58
	 *@param iRequest
	 *@param workerAssignId
	 *@param sscTaskPool
	 *@return boolean
	 *@Version 1.0
	 **/
	boolean validateNodeWorkerRule(IRequest iRequest, Long workerAssignId, SscTaskPool sscTaskPool);
}