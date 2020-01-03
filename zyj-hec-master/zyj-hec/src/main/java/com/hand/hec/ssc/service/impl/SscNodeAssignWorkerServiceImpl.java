package com.hand.hec.ssc.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.ssc.mapper.SscNodeAssignWorkerMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hand.hec.ssc.dto.SscNodeAssignWorker;
import com.hand.hec.ssc.service.ISscNodeAssignWorkerService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class SscNodeAssignWorkerServiceImpl extends BaseServiceImpl<SscNodeAssignWorker> implements ISscNodeAssignWorkerService{

	@Autowired
	SscNodeAssignWorkerMapper mapper;

	@Override
	public List<SscNodeAssignWorker> querySscNodeAssignWorker(@Param("nodeId") Long nodeId, IRequest iRequest) {
		return mapper.querySscNodeAssignWorker(nodeId);
	}

    @Override
    public List<SscNodeAssignWorker> querySscNodeAssignWorkerByDispatch(IRequest iRequest, Long dispatchRecordId) {
        return mapper.querySscNodeAssignWorkerByDispatch(dispatchRecordId);
    }

    @Override
    public List<SscNodeAssignWorker> getAllNodeAssignWorker(Long workTeamId, Long employeeId) {
        return mapper.getAllNodeAssignWorker(workTeamId,employeeId);
    }
}