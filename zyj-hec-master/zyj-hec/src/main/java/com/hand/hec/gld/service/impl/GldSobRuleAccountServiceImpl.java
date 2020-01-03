package com.hand.hec.gld.service.impl;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.gld.dto.GldSobRuleAccount;
import com.hand.hec.gld.mapper.GldSobRuleAccountMapper;
import com.hand.hec.gld.service.IGldSobRuleAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class GldSobRuleAccountServiceImpl extends BaseServiceImpl<GldSobRuleAccount> implements IGldSobRuleAccountService {

	@Autowired
	GldSobRuleAccountMapper gldSobRuleAccountMapper;

	@Override
	public List<GldSobRuleAccount> queryGldSobRuleAccount(IRequest requestContext, GldSobRuleAccount dto, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		return gldSobRuleAccountMapper.queryGldSobRuleAccount(dto);
	}

	@Override
	public List<GldSobRuleAccount> queryNotRefAccount(IRequest requestContext, GldSobRuleAccount dto, int pageNum,
			int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		return gldSobRuleAccountMapper.queryNotRefAccount(dto);
	}
}