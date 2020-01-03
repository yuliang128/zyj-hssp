package com.hand.hec.csh.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.csh.mapper.CshMoPayUsdRefFlowItMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hand.hec.csh.dto.CshMoPayUsdRefFlowIt;
import com.hand.hec.csh.service.ICshMoPayUsdRefFlowItService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 付款用途联系现金流量项Service实现类
 * </p>
 *
 * @author yang.cai@hand-chian.com 2019/02/26 19:48
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class CshMoPayUsdRefFlowItServiceImpl extends BaseServiceImpl<CshMoPayUsdRefFlowIt>
                implements ICshMoPayUsdRefFlowItService {
    @Autowired
    CshMoPayUsdRefFlowItMapper mapper;

    @Override
    public List<CshMoPayUsdRefFlowIt> queryIncludeSetOfBooks(IRequest request,
                    CshMoPayUsdRefFlowIt cshMoPayUsdRefFlowIt, int pageNum, int pageSize) {
        return mapper.queryIncludeSetOfBooks(cshMoPayUsdRefFlowIt);
    }

    /**
     * <p>
     * 获取现金流量项(对应exp_util_pkg.get_cash_flow_item_id)
     * <p/>
     *
     * @param paymentUsedeId 付款用途ID
     * @param accEntityId 核算主体ID
     * @return 现金流量项ID
     * @author yang.duan 2019/3/13 18:38
     */
    @Override
    public Long getCashFlowItemId(Long paymentUsedeId, Long accEntityId) {
        return mapper.getCashFlowItemId(paymentUsedeId, accEntityId);
    }
}
