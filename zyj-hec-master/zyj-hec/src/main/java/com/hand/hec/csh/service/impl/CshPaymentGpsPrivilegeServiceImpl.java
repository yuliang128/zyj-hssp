package com.hand.hec.csh.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.csh.dto.CshPaymentGpsAsgnAe;
import com.hand.hec.csh.dto.CshPaymentGpsPrivilege;
import com.hand.hec.csh.mapper.CshPaymentGpsPrivilegeMapper;
import com.hand.hec.csh.service.ICshPaymentGpsPrivilegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class CshPaymentGpsPrivilegeServiceImpl extends BaseServiceImpl<CshPaymentGpsPrivilege> implements ICshPaymentGpsPrivilegeService{

	@Autowired
	private CshPaymentGpsPrivilegeMapper mapper;

	@Override
	public List<CshPaymentGpsAsgnAe> selectCshPaymentGpsPrivilege(Long assignAeId,IRequest iRequest) {
		return mapper.selectCshPaymentGpsPrivilege(assignAeId);
	}
}