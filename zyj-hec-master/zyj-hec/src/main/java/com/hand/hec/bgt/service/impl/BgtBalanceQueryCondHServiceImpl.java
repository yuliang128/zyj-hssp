package com.hand.hec.bgt.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.bgt.dto.BgtBalanceQueryCondH;
import com.hand.hec.bgt.mapper.BgtBalanceQueryCondHMapper;
import com.hand.hec.bgt.service.IBgtBalanceQueryCondHService;

/**
 * <p>
 * 预算余额查询方案头serviceImpl
 * </p>
 *
 * @author YHL 2019/03/13 18:22
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class BgtBalanceQueryCondHServiceImpl extends BaseServiceImpl<BgtBalanceQueryCondH>
        implements IBgtBalanceQueryCondHService {

    @Autowired
    private BgtBalanceQueryCondHMapper mapper;

    @Override
    public List<BgtBalanceQueryCondH> getBalanceQueryCondH(IRequest request, Long bgtOrgId) {
        return mapper.getBalanceQueryCondH(bgtOrgId);
    }

}