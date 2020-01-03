package com.hand.hec.fnd.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.fnd.mapper.GldAccountHierarchyDetailMapper;
import com.hand.hec.fnd.service.IGldAccountHierarchyDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hand.hec.fnd.dto.GldAccountHierarchyDetail;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * GldAccountHierarchyDetailServiceImpl
 * </p>
 *
 * @author guiyu 2019/01/08 15:37
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class GldAccountHierarchyDetailServiceImpl extends BaseServiceImpl<GldAccountHierarchyDetail>
                implements IGldAccountHierarchyDetailService {

    @Autowired
    public GldAccountHierarchyDetailMapper mapper;

    @Override
    public void deleteByAccountSetId(Long accountSetId) {
        mapper.deleteByAccountSetId(accountSetId);
    }
}
