package com.hand.hec.csh.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.csh.dto.CshPaymentGpsRefEmp;
import com.hand.hec.csh.mapper.CshPaymentGpsRefEmpMapper;
import com.hand.hec.csh.service.ICshPaymentGpsRefEmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class CshPaymentGpsRefEmpServiceImpl extends BaseServiceImpl<CshPaymentGpsRefEmp> implements ICshPaymentGpsRefEmpService{

	@Autowired
	private CshPaymentGpsRefEmpMapper mapper;

	@Override
	public List<CshPaymentGpsRefEmp> selectCshPaymentGpsRefEmp(Long groupId,IRequest iRequest) {
		return mapper.selectCshPaymentGpsRefEmp(groupId);
	}
}