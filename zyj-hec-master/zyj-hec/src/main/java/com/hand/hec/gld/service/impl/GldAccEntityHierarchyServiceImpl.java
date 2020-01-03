package com.hand.hec.gld.service.impl;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.gld.dto.GldAccEntityHierarchy;
import com.hand.hec.gld.mapper.GldAccEntityHierarchyMapper;
import com.hand.hec.gld.service.IGldAccEntityHierarchyService;

@Service
@Transactional(rollbackFor = Exception.class)
public class GldAccEntityHierarchyServiceImpl extends BaseServiceImpl<GldAccEntityHierarchy> implements IGldAccEntityHierarchyService {

    @Autowired
    private GldAccEntityHierarchyMapper mapper;

    @Override
    public List<GldAccEntityHierarchy> selectChild(Long parentEntityId, IRequest request, Integer page, Integer pageSize) {
        if(page != null && pageSize != null) {
            PageHelper.startPage(page, pageSize);
        }
        return mapper.selectChild(parentEntityId);
    }

    @Override
    public List<GldAccEntityHierarchy> selectTree() { return mapper.selectTree(); }
}