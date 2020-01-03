package com.hand.hec.csh.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.csh.dto.CshPaymentGroup;
import com.hand.hec.csh.mapper.CshPaymentGroupMapper;
import com.hand.hec.csh.service.ICshPaymentGroupService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class CshPaymentGroupServiceImpl extends BaseServiceImpl<CshPaymentGroup> implements ICshPaymentGroupService{

	@Autowired
	private CshPaymentGroupMapper mapper;

	@Override
	public List<CshPaymentGroup> selectCshPaymentGroup(@Param("magOrgId") Long magOrgId, @Param("groupCode") String groupCode, @Param("description") String description,IRequest iRequest) {
		return mapper.selectCshPaymentGroup(magOrgId,groupCode,description);
	}
}