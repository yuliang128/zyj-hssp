package com.hand.hec.gld.service.impl;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.gld.dto.GldSobRuleGpRefAe;
import com.hand.hec.gld.mapper.GldSobRuleGpRefAeMapper;
import com.hand.hec.gld.service.IGldSobRuleGpRefAeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class GldSobRuleGpRefAeServiceImpl extends BaseServiceImpl<GldSobRuleGpRefAe> implements IGldSobRuleGpRefAeService {

	@Autowired
	GldSobRuleGpRefAeMapper gldSobRuleGpRefAeMapper;


	@Override
	public List<GldSobRuleGpRefAe> queryNotRefAE(IRequest request, GldSobRuleGpRefAe condition, int pageNum,
			int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		return gldSobRuleGpRefAeMapper.queryNotRefAE(condition);
	}
}