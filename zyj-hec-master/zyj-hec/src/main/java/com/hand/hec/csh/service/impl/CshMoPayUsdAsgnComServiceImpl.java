package com.hand.hec.csh.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.csh.mapper.CshMoPayUsdAsgnComMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hand.hec.csh.dto.CshMoPayUsdAsgnCom;
import com.hand.hec.csh.service.ICshMoPayUsdAsgnComService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
/**
 * <p>
 * 付款用途分配公司Service实现类
 * </p>
 *
 * @author yang.cai@hand-chian.com 2019/02/26 19:48
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class CshMoPayUsdAsgnComServiceImpl extends BaseServiceImpl<CshMoPayUsdAsgnCom> implements ICshMoPayUsdAsgnComService{
    @Autowired
    CshMoPayUsdAsgnComMapper mapper;
    @Override
    public List<CshMoPayUsdAsgnCom> queryLov(IRequest request, Long magOrgId, Long paymentUsedeId, int pageNum, int pageSize) {
        return mapper.queryLov(magOrgId,paymentUsedeId);
    }

    @Override
    public List<CshMoPayUsdAsgnCom> query(IRequest request, CshMoPayUsdAsgnCom cshMoPayUsdAsgnCom, int pageNum, int pageSize) {
        return mapper.query(cshMoPayUsdAsgnCom);
    }
}