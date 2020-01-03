package com.hand.hec.csh.service.impl;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.csh.dto.CshMoPayReqRefTrxCls;
import com.hand.hec.csh.mapper.CshMoPayReqRefTrxClsMapper;
import com.hand.hec.csh.service.ICshMoPayReqRefTrxClsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class CshMoPayReqRefTrxClsServiceImpl extends BaseServiceImpl<CshMoPayReqRefTrxCls> implements ICshMoPayReqRefTrxClsService {
    @Autowired
    CshMoPayReqRefTrxClsMapper mapper;

    @Override
    public List<CshMoPayReqRefTrxCls> queryByMagOrgId(IRequest request, CshMoPayReqRefTrxCls condition, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return mapper.queryByMagOrgId(condition);
    }

    @Override
	public List<CshMoPayReqRefTrxCls> queryForLoan(IRequest request, Long moPaymentReqTypeId){
    	return mapper.queryForLoan(moPaymentReqTypeId);
	}
}