package com.hand.hec.ssc.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.ssc.dto.SscWorkCenterDocType;
import com.hand.hec.ssc.mapper.SscWorkCenterDocTypeMapper;
import com.hand.hec.ssc.service.ISscWorkCenterDocTypeService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class SscWorkCenterDocTypeServiceImpl extends BaseServiceImpl<SscWorkCenterDocType> implements ISscWorkCenterDocTypeService{

	@Autowired
	private SscWorkCenterDocTypeMapper mapper;
	@Override
	public List<SscWorkCenterDocType> querySscWorkCenterDocType(@Param("scopeId") Long scopeId, @Param("magOrgId") Long magOrgId, IRequest iRequest) {
		return mapper.querySscWorkCenterDocType(scopeId,magOrgId);
	}
}