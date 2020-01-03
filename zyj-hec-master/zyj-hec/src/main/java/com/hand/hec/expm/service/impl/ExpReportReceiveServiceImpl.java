package com.hand.hec.expm.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.hand.hec.expm.dto.ExpReportReceive;
import com.hand.hec.expm.service.IExpReportReceiveService;
import org.springframework.transaction.annotation.Transactional;
/**
 * <p>
 * ExpReportReceiveServiceImpl
 * </p>
 *
 * @author yang.duan 2019/01/10 15:05
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ExpReportReceiveServiceImpl extends BaseServiceImpl<ExpReportReceive> implements IExpReportReceiveService{

}