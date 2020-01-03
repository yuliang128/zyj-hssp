package com.hand.hec.ssc.service;

import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.ssc.dto.SscWorker;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ISscWorkerService extends IBaseService<SscWorker>, ProxySelf<ISscWorkerService>{
	/**
	 * 根据workTeamId作业人员
	 *Param workTeamId
	 * @return SscWorker
	 * @author bo.zhang 2019-03-11 19:24:36
	 */
	List<SscWorker> querySscWorker(@Param("workTeamId") Long workTeamId);

	/**
	 *获取员工所在工作组数据
	 *
	 *@Author hui.zhao01@hand-china.com
	 *@Date 2019/3/27 15:25
	 *@param employeeId 员工Id
	 *@return
	 *@Version 1.0
	 **/
    List<SscWorker> getAllWorkTeam(Long employeeId);
}