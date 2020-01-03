package com.hand.hec.ssc.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.ssc.dto.SscTaskPoolPrepare;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface ISscTaskPoolPrepareService extends IBaseService<SscTaskPoolPrepare>, ProxySelf<ISscTaskPoolPrepareService>{
	/**
	 *根据流程代码，秒速，单据类别查询
	 *@Param workflowCode,description,docCategory
	 * @return SscTaskPoolPrepare
	 * @author bo.zhang 2019-03-25 15:24:36
	 */
	List<SscTaskPoolPrepare> selectSscTaskPoolPrepare(IRequest iRequest,
													  @Param("docNumber") String docNumber,
													  @Param("docCategory") String docCategory,
													  @Param("taskStatus") String taskStatus,
													  @Param("dispatchStatus") String dispatchStatus,
													  @Param("workEmployeeId") Long workEmployeeId,
													  @Param("docEmployeeId") Long docEmployeeId,
													  @Param("magOrgId") Long magOrgId,
													  @Param("companyId") Long companyId,
													  @Param("workflowId") Long workflowId,
													  @Param("nodeId") Long nodeId,
													  @Param("enterTime" )Date enterTime,
													  @Param("enterTimeTo")Date enterTimeTo,
													  @Param("exitTime")Date exitTime,
													  @Param("exitTimeTo")Date exitTimeTo);

	/**
	 *根据taskId查出CurAdviceEmpName
	 *@Param taskId
	 * @return String
	 * @author bo.zhang 2019-03-26 15:24:36
	 */
	List<String>selectCurAdviceEmpName(@Param("taskId") Long taskId);

}