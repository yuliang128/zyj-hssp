package com.hand.hec.ssc.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.ssc.mapper.SscWorkCenterBusiScopeMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hand.hec.ssc.dto.SscWorkCenterBusiScope;
import com.hand.hec.ssc.service.ISscWorkCenterBusiScopeService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class SscWorkCenterBusiScopeServiceImpl extends BaseServiceImpl<SscWorkCenterBusiScope> implements ISscWorkCenterBusiScopeService{

	@Autowired
	private SscWorkCenterBusiScopeMapper mapper;
	@Override
	public List<SscWorkCenterBusiScope> querySscWorkCenterBusiScope(IRequest iRequest, @Param("workCenterId") Long workCenterId) {
		return mapper.querySscWorkCenterBusiScope(workCenterId);
	}
}