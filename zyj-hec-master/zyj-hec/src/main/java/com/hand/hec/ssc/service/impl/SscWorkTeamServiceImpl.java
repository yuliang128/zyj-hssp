package com.hand.hec.ssc.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.gld.exception.GldSetOfBookException;
import com.hand.hec.ssc.dto.SscWorkTeam;
import com.hand.hec.ssc.exception.SscWorkTeamException;
import com.hand.hec.ssc.mapper.SscWorkTeamMapper;
import com.hand.hec.ssc.service.ISscWorkTeamService;

import com.github.pagehelper.PageHelper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional(rollbackFor = Exception.class)
public class SscWorkTeamServiceImpl extends BaseServiceImpl<SscWorkTeam> implements ISscWorkTeamService{

	@Autowired
	private SscWorkTeamMapper mapper;

	@Override
	public List<SscWorkTeam> querySscWorkTeam(@Param("workCenterId") Long workCenterId, @Param("workTeamCode") String workTeamCode, @Param("description") String description,IRequest iRequest) {
		return mapper.querySscWorkTeam(workCenterId,workTeamCode,description);
	}

	@Override
	public boolean notMutualSuperior(Long workTeamId, Long parentWorkTeamId, List<SscWorkTeam> sscWorkTeams,int length) {
		if(length==1){
			return true;
		}
		for(int i=0;i<sscWorkTeams.size();i++) {
			if (parentWorkTeamId != null) {
				if (parentWorkTeamId.equals(sscWorkTeams.get(i).getWorkTeamId())) {
					Long pid = sscWorkTeams.get(i).getParentWorkTeamId();
					if (pid == null) {
						return true;
					} else if (pid.equals(workTeamId)) {
						return false;
					} else {
						return notMutualSuperior(workTeamId, pid, sscWorkTeams, --length);
					}
				}
			}
		}
		return true;
	}

	@Override
	public void notMutualSuperiorException(boolean flag) throws SscWorkTeamException {
		if(!flag){
			throw new SscWorkTeamException(SscWorkTeamException.NOT_MUTUAL_SUPERIOR, SscWorkTeamException.NOT_MUTUAL_SUPERIOR, null);
		}
	}

	@Override
	public List<SscWorkTeam> queryEnableTeam(Long workCenterId,String workTeamCode, String workTeamName, IRequest request) {

		List<SscWorkTeam> sscWorkTeamList = mapper.select(SscWorkTeam.builder().enabledFlag("Y").workCenterId(workCenterId).workTeamCode(workTeamCode).
				description(workTeamName).build());

		for(SscWorkTeam sscWorkTeam:sscWorkTeamList){
			sscWorkTeam.setWorkTeamCodeName(sscWorkTeam.getWorkTeamCode()+"-"+sscWorkTeam.getDescription());
		}

		return sscWorkTeamList;
	}

	@Override
	public List<Map> queryEmpOfUserOnTeam(Long workCenterId, String workTeamName, String employeeName, IRequest request, Integer page, Integer pageSize) {
		if(page != null && pageSize != null) {
			PageHelper.startPage(page, pageSize);
		}
		return mapper.queryEmpOfUserOnTeam(workCenterId,workTeamName,employeeName);
	}

}