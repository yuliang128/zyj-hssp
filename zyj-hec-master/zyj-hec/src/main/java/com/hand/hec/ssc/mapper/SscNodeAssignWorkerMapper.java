package com.hand.hec.ssc.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.ssc.dto.SscNodeAssignWorker;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SscNodeAssignWorkerMapper extends Mapper<SscNodeAssignWorker>{
	/**
	 *
	 *@Param nodeId
	 * @return SscNodeAssignWorker
	 * @author bo.zhang 2019-03-20 19:24:36
	 */
	List<SscNodeAssignWorker> querySscNodeAssignWorker(@Param("nodeId") Long nodeId);

	/**
	 *获取工作流程节点分配人员
	 *@Author hui.zhao01@hand-china.com
	 *@Date 2019/3/27 9:40
	 *@param iRequest 请求上下文
	 *@param dispatchRecordId 派工记录Id
	 *@return List<SscNodeAssignWorker>
	 *@Version 1.0
	 **/
	List<SscNodeAssignWorker> querySscNodeAssignWorkerByDispatch(@Param("dispatchRecordId")Long dispatchRecordId);

	/**
	 *获取该员工所有的流程节点
	 *@Author hui.zhao01@hand-china.com
	 *@Date 2019/3/27 9:40
	 *@param workTeamId 工作组Id
	 *@param employeeId 员工Id
	 *@return List<SscNodeAssignWorker>
	 *@Version 1.0
	 **/
	List<SscNodeAssignWorker> getAllNodeAssignWorker(@Param("workTeamId")Long workTeamId,@Param("employeeId") Long employeeId);
}