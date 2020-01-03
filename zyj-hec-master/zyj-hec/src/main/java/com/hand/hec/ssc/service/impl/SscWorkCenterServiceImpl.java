package com.hand.hec.ssc.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.ssc.dto.SscWorkCenter;
import com.hand.hec.ssc.mapper.SscWorkCenterMapper;
import com.hand.hec.ssc.service.ISscWorkCenterService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class SscWorkCenterServiceImpl extends BaseServiceImpl<SscWorkCenter> implements ISscWorkCenterService{

	@Autowired
	private SscWorkCenterMapper mapper;

	@Override
	public List<SscWorkCenter> querySscWorkCenter(IRequest iRequest,
												  @Param("workCenterCode") String workCenterCode,
												  @Param("description") String description) {
		return mapper.querySscWorkCenter(workCenterCode,description);
	}

	@Override
	public List<SscWorkCenter> selectSscWorkCenter(IRequest iRequest, @Param("name") String name, @Param("description") String description, @Param("workTeamName") String workTeamName) {
		return mapper.selectSscWorkCenter(name, description, workTeamName);
	}

	@Override
	public List<SscWorkCenter> combox(SscWorkCenter sscWorkCenter, IRequest request) {

		return mapper.queryCenterByUser(sscWorkCenter.getWorkCenterCode(),sscWorkCenter.getWorkCenterCodeName());

	}
}