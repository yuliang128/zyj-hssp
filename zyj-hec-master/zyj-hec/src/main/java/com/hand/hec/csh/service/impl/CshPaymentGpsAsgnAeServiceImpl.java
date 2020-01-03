package com.hand.hec.csh.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.csh.mapper.CshPaymentGpsAsgnAeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hand.hec.csh.dto.CshPaymentGpsAsgnAe;
import com.hand.hec.csh.service.ICshPaymentGpsAsgnAeService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class CshPaymentGpsAsgnAeServiceImpl extends BaseServiceImpl<CshPaymentGpsAsgnAe> implements ICshPaymentGpsAsgnAeService{

	@Autowired
	private CshPaymentGpsAsgnAeMapper mapper;

	@Override
	public List<CshPaymentGpsAsgnAe> selectCshPaymentGpsAsgnAe(Long groupId, IRequest iRequest) {
		return mapper.selectCshPaymentGpsAsgnAe(groupId);
	}
}