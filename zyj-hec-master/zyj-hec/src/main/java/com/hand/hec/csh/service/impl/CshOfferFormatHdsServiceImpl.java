package com.hand.hec.csh.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.csh.dto.CshOfferFormatHds;
import com.hand.hec.csh.mapper.CshOfferFormatHdsMapper;
import com.hand.hec.csh.service.ICshOfferFormatHdsService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class CshOfferFormatHdsServiceImpl extends BaseServiceImpl<CshOfferFormatHds> implements ICshOfferFormatHdsService{

	@Autowired
	CshOfferFormatHdsMapper mapper;

	@Override
	public List<CshOfferFormatHds> querySysCode(IRequest iRequest) {
		return mapper.querySysCode();
	}

	@Override
	public List<CshOfferFormatHds> queryCshOfferFormatHds(@Param("formatCode") String formatCode, @Param("description") String description,IRequest iRequest) {
		return mapper.queryCshOfferFormatHds(formatCode,description);
	}
}