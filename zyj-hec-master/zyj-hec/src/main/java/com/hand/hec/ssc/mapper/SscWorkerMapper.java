package com.hand.hec.ssc.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.ssc.dto.SscWorker;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SscWorkerMapper extends Mapper<SscWorker>{

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
	 *@Date 2019/3/27 15:26
	 *@param employeeId 员工Id
	 *@return List<SscWorker>
	 *@Version 1.0
	 **/
	List<SscWorker> getAllWorkTeam(@Param("employeeId") Long employeeId);
}