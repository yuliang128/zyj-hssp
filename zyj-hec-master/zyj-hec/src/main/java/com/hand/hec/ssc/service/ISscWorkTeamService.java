package com.hand.hec.ssc.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.ssc.dto.SscWorkTeam;
import com.hand.hec.ssc.exception.SscWorkTeamException;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ISscWorkTeamService extends IBaseService<SscWorkTeam>, ProxySelf<ISscWorkTeamService>{
	/**
	 * 查出共享作业组
	 *Param workCenterId,workTeamCode,description
	 * @return
	 * @author bo.zhang 2019-03-11 19:24:36
	 */
	List<SscWorkTeam> querySscWorkTeam(@Param("workCenterId") Long workCenterId,
											  @Param("workTeamCode") String workTeamCode,
											  @Param("description") String description,IRequest iRequest);

	/**
	 * 校验  不能互为上级工作组
	 * Param workTeamId,worparentWorkTeamId,List<SscWorkTeam>
	 * @return true/false
	 * @author bo.zhang 2019-03-11 19:24:36
	 */
	 boolean notMutualSuperior(Long workTeamId,Long parentWorkTeamId,List<SscWorkTeam> sscWorkTeams,int length);

	 void notMutualSuperiorException(boolean flag) throws SscWorkTeamException;

	/**
	 * 获取工作中心下启用的工作组
	 *
	 * @param workCenterId 工作中心ID
	 * @param workTeamCode 工作组代码
	 * @param workTeamName 工作组名称
	 * @param request 上下文
	 * @author ngls.luhui 2019-03-25 19:05
	 * @return java.util.List<com.hand.hec.ssc.dto.SscWorkTeam>
	 */
	 List<SscWorkTeam> queryEnableTeam(Long workCenterId,String workTeamCode,String workTeamName,IRequest request);

	/**
	 * 当前员工为工作组负责人下的所有员工（含工作中心条件）
	 *
	 * @param workCenterId 工作中心ID
	 * @param workTeamName 工作组名称
	 * @param employeeName 员工名称
	 * @param request 上下文
	 * @param page
	 * @param pageSize
	 * @author ngls.luhui 2019-03-27 15:26
	 * @return java.util.List<java.util.Map>
	 */
	List<Map> queryEmpOfUserOnTeam(Long workCenterId,String workTeamName,String employeeName,IRequest request,Integer page,Integer pageSize);
}