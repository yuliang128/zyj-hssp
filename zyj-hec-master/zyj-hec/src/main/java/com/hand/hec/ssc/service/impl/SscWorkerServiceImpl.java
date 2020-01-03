package com.hand.hec.ssc.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.ssc.dto.SscWorker;
import com.hand.hec.ssc.mapper.SscWorkerMapper;
import com.hand.hec.ssc.service.ISscWorkerService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class SscWorkerServiceImpl extends BaseServiceImpl<SscWorker> implements ISscWorkerService{

	@Autowired
	private SscWorkerMapper mapper;

	@Override
	public List<SscWorker> querySscWorker(@Param("workTeamId") Long workTeamId) {
		return mapper.querySscWorker(workTeamId);
	}

    @Override
    public List<SscWorker> getAllWorkTeam(Long employeeId) {
        return mapper.getAllWorkTeam(employeeId);
    }
}