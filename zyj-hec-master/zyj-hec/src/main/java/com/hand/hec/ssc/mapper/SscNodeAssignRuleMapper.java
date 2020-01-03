package com.hand.hec.ssc.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.ssc.dto.SscNodeAssignRule;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface SscNodeAssignRuleMapper extends Mapper<SscNodeAssignRule>{
	/**
	 *工作中心派工规则分配  联动
	 *@Param workerAssignId
	 * @return SscNodeAssignRule
	 * @author bo.zhang 2019-03-20 19:24:36
	 */
	List<SscNodeAssignRule> querySscNodeAssignRule(@Param("workerAssignId") Long workerAssignId);

	/**
	 *<p>TODO<p>
	 *@Author hui.zhao01@hand-china.com
	 *@Date 2019/3/27 10:00
	 *@param workerAssignId
	 *@param currentDate
	 *@return
	 *@Version 1.0
	 **/
	List<SscNodeAssignRule> queryValidateNodeWorkerRule(@Param("workerAssignId") Long workerAssignId,@Param("currentDate") Date currentDate);
}