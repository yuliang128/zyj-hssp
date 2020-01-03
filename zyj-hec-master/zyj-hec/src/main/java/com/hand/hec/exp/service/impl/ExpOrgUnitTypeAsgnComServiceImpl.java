package com.hand.hec.exp.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.exp.dto.ExpOrgUnitTypeAsgnCom;
import com.hand.hec.exp.mapper.ExpOrgUnitTypeAsgnComMapper;
import com.hand.hec.exp.service.IExpOrgUnitTypeAsgnComService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  ExpOrgUnitTypeAsgnComServiceImpl
 * </p>
 * 
 * @author yang.duan 2019/01/10 11:19
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ExpOrgUnitTypeAsgnComServiceImpl extends BaseServiceImpl<ExpOrgUnitTypeAsgnCom> implements IExpOrgUnitTypeAsgnComService{


	@Autowired
	ExpOrgUnitTypeAsgnComMapper expOrgUnitTypeAsgnComMapper;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public List<ExpOrgUnitTypeAsgnCom> batchAssignComAll(IRequest request, List<ExpOrgUnitTypeAsgnCom> list) {
		return list.stream().map(expOrgUnitTypeAsgnCom -> {
			List<ExpOrgUnitTypeAsgnCom>  expOrgUnitTypeAsgnComList = expOrgUnitTypeAsgnComMapper.select(expOrgUnitTypeAsgnCom);
			if( expOrgUnitTypeAsgnComList == null || expOrgUnitTypeAsgnComList.isEmpty() ){
				return self().insertSelective(request, expOrgUnitTypeAsgnCom);
			}
			return null;
		}).collect(Collectors.toList());
	}
}