package com.hand.hec.csh.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.csh.dto.CshOfferFormatAsgnAe;
import com.hand.hec.csh.mapper.CshOfferFormatAsgnAeMapper;
import com.hand.hec.csh.service.ICshOfferFormatAsgnAeService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class CshOfferFormatAsgnAeServiceImpl extends BaseServiceImpl<CshOfferFormatAsgnAe> implements ICshOfferFormatAsgnAeService{

	@Autowired
	private  CshOfferFormatAsgnAeMapper mapper;

	@Override
	public List<CshOfferFormatAsgnAe> queryCshOfferFormatAsgnAe(@Param("formatHdsId") Long formatHdsId,IRequest iRequest) {
		return mapper.queryCshOfferFormatAsgnAe(formatHdsId);
	}

	@Override
	public List<CshOfferFormatAsgnAe> selectCshOfferFormatAsgnAe(@Param("formatHdsId") Long formatHdsId,IRequest iRequest) {
		return mapper.selectCshOfferFormatAsgnAe(formatHdsId);
	}
}