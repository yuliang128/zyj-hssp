package com.hand.hec.expm.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.hand.hec.expm.dto.ExpReportObjectsLayout;
import com.hand.hec.expm.service.IExpReportObjectsLayoutService;
import org.springframework.transaction.annotation.Transactional;
/**
 * <p>
 * ExpReportObjectsLayoutServiceImpl
 * </p>
 *
 * @author yang.duan 2019/01/10 15:04
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ExpReportObjectsLayoutServiceImpl extends BaseServiceImpl<ExpReportObjectsLayout> implements IExpReportObjectsLayoutService{

}