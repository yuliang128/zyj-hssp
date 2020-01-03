package com.hand.hec.bgt.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hand.hec.bgt.dto.BgtCenterHierarchy;
import com.hand.hec.bgt.mapper.BgtCenterHierarchyMapper;
import com.hand.hec.bgt.service.IBgtCenterHierarchyService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 预算中心层次ServiceImpl
 * </p>
 * 
 * @author mouse 2019/01/07 16:50
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class BgtCenterHierarchyServiceImpl extends BaseServiceImpl<BgtCenterHierarchy> implements IBgtCenterHierarchyService{

    @Autowired
    private BgtCenterHierarchyMapper bgtCenterHierarchyMapper;

    @Override
    public List<BgtCenterHierarchy> queryDetailsCanAsgn(Long centerId, Long bgtOrgId, IRequest request, Integer page, Integer pageSize) {
        if(page != null && pageSize != null) {
            PageHelper.startPage(page, pageSize);
        }
        return bgtCenterHierarchyMapper.queryDetailsCanAsgn(centerId,bgtOrgId);
    }
}