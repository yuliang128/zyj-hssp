package com.hand.hec.expm.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.hand.hec.expm.dto.ExpReportInterfaceLog;
import com.hand.hec.expm.service.IExpReportInterfaceLogService;
import org.springframework.transaction.annotation.Transactional;
/**
 * <p>
 * ExpReportInterfaceLogServiceImpl
 * </p>
 *
 * @author yang.duan 2019/01/10 15:04
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ExpReportInterfaceLogServiceImpl extends BaseServiceImpl<ExpReportInterfaceLog> implements IExpReportInterfaceLogService{

}