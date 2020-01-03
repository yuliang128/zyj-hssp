package com.hand.hec.fnd.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.hand.hec.fnd.dto.FndCompanyHierarchyDetail;
import com.hand.hec.fnd.service.IFndCompanyHierarchyDetailService;
import org.springframework.transaction.annotation.Transactional;
/**
 * <p>
 * FndCompanyHierarchyDetailServiceImpl
 * </p>
 * 
 * @author yang.duan 2019/01/10 11:07
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class FndCompanyHierarchyDetailServiceImpl extends BaseServiceImpl<FndCompanyHierarchyDetail> implements IFndCompanyHierarchyDetailService{

}