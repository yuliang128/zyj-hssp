package com.hand.hec.bgt.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.hand.hec.bgt.dto.BgtEntityHierarchy;
import com.hand.hec.bgt.service.IBgtEntityHierarchyService;
import org.springframework.transaction.annotation.Transactional;
/**
 * <p>
 * 预算实体层次ServiceImpl
 * </p>
 * 
 * @author mouse 2019/01/07 16:51
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class BgtEntityHierarchyServiceImpl extends BaseServiceImpl<BgtEntityHierarchy> implements IBgtEntityHierarchyService{

}