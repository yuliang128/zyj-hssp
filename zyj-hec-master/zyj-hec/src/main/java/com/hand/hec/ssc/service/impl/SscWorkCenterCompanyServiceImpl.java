package com.hand.hec.ssc.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.ssc.mapper.SscWorkCenterCompanyMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hand.hec.ssc.dto.SscWorkCenterCompany;
import com.hand.hec.ssc.service.ISscWorkCenterCompanyService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class SscWorkCenterCompanyServiceImpl extends BaseServiceImpl<SscWorkCenterCompany> implements ISscWorkCenterCompanyService{

	@Autowired
	private SscWorkCenterCompanyMapper mapper;

	@Override
	public List<SscWorkCenterCompany> selectSscWorkCenterCompany(IRequest iRequest, @Param("scopeId") Long scopeId) {
		return mapper.selectSscWorkCenterCompany(scopeId);
	}
}