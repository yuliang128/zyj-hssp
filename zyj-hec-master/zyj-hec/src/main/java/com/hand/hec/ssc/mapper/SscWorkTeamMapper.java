package com.hand.hec.ssc.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.ssc.dto.SscWorkTeam;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface SscWorkTeamMapper extends Mapper<SscWorkTeam>{
	/**
	 * 查出共享作业组
	 *Param workCenterId,workTeamCode,description
	 * @return
	 * @author bo.zhang 2019-03-11 19:24:36
	 */
	List<SscWorkTeam> querySscWorkTeam(@Param("workCenterId") Long workCenterId,
											  @Param("workTeamCode") String workTeamCode,
											  @Param("description") String description);
	
	
	/**
	 * 当前员工为工作组负责人下的所有员工（含工作中心条件）
	 *
	 * @param workCenterId 工作中心ID
	 * @param workTeamName 工作组名称
	 * @param employeeName 员工名称
	 * @author ngls.luhui 2019-03-27 15:26
	 * @return java.util.List<java.util.Map>
	 */
	List<Map> queryEmpOfUserOnTeam(@Param("workCenterId") Long workCenterId,
								   @Param("workTeamName") String workTeamName,
								   @Param("employeeName") String employeeName);

}