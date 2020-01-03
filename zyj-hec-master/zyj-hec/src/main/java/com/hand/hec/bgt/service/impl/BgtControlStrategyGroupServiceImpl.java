package com.hand.hec.bgt.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.bgt.mapper.BgtControlStrategyGroupMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hand.hec.bgt.dto.BgtControlStrategyGroup;
import com.hand.hec.bgt.service.IBgtControlStrategyGroupService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 预算控制策略组ServiceImpl
 * </p>
 * 
 * @author mouse 2019/01/07 16:51
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class BgtControlStrategyGroupServiceImpl extends BaseServiceImpl<BgtControlStrategyGroup>
                implements IBgtControlStrategyGroupService {
    @Autowired
    private BgtControlStrategyGroupMapper controlStrategyGroupMapper;

    @Override
    public List<BgtControlStrategyGroup> queryByBgtOrgId(IRequest request, Long bgtOrgId, Long magOrgId) {
        return controlStrategyGroupMapper.queryByBgtOrgId(bgtOrgId, magOrgId);
    }
}
