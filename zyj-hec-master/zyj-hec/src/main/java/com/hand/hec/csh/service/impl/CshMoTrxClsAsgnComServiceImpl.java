package com.hand.hec.csh.service.impl;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.csh.dto.CshMoTrxClsAsgnCom;
import com.hand.hec.csh.mapper.CshMoTrxClsAsgnComMapper;
import com.hand.hec.csh.service.ICshMoTrxClsAsgnComService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 现金事物分类分配公司impl
 * </p>
 *
 * @author LJK 2019/02/19 12:08
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class CshMoTrxClsAsgnComServiceImpl extends BaseServiceImpl<CshMoTrxClsAsgnCom>
		implements ICshMoTrxClsAsgnComService {

	@Autowired
	CshMoTrxClsAsgnComMapper mapper;

	@Override
	public List<CshMoTrxClsAsgnCom> queryByTrxClassId(IRequest request, Long magOrgId, Long moCshTrxClassId,
			int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		return mapper.queryByTrxClassId(magOrgId,moCshTrxClassId);
	}

	@Override
	public List<CshMoTrxClsAsgnCom> queryComByTrxClassId(IRequest request, Long magOrgId, Long moCshTrxClassId, int pageNum,
			int pageSize){
		PageHelper.startPage(pageNum, pageSize);
		return mapper.queryComByTrxClassId(magOrgId,moCshTrxClassId);
	}
}