package com.hand.hec.csh.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.csh.mapper.CshOfferFormatLnsMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hand.hec.csh.dto.CshOfferFormatLns;
import com.hand.hec.csh.service.ICshOfferFormatLnsService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class CshOfferFormatLnsServiceImpl extends BaseServiceImpl<CshOfferFormatLns> implements ICshOfferFormatLnsService{

	@Autowired
	private CshOfferFormatLnsMapper mapper;

	@Override
	public List<CshOfferFormatLns> queryCshOfferFormatLns(@Param("formatHdsId") Long formatHdsId) {
		return mapper.queryCshOfferFormatLns(formatHdsId);
	}

	@Override
	public Long querycolumnNameMax() {
		return mapper.querycolumnNameMax();
	}

	@Override
	public boolean updateColumnFormat(@Param("formatLnsId") Long formatLnsId) {
		return mapper.updateColumnFormat(formatLnsId);
	}
}