package com.hand.hec.gld.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.hand.hec.gld.dto.GldSobRespCenterHierarchy;
import com.hand.hec.gld.service.IGldSobRespCenterHierarchyService;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 子成本中心层级serviceImpl
 * </p>
 *
 * @author wuxiuxian 2019-01-10
 */

@Service
@Transactional(rollbackFor = Exception.class)
public class GldSobRespCenterHierarchyServiceImpl extends BaseServiceImpl<GldSobRespCenterHierarchy> implements IGldSobRespCenterHierarchyService {
    @Override
    protected boolean useSelectiveUpdate(){
        return false;
    }
}