package com.hand.hec.expm.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.hand.hec.expm.dto.ExpReportPmtSchTaxLine;
import com.hand.hec.expm.service.IExpReportPmtSchTaxLineService;
import org.springframework.transaction.annotation.Transactional;
/**
 * <p>
 * ExpReportPmtSchTaxLineServiceImpl
 * </p>
 *
 * @author yang.duan 2019/01/10 15:05
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ExpReportPmtSchTaxLineServiceImpl extends BaseServiceImpl<ExpReportPmtSchTaxLine> implements IExpReportPmtSchTaxLineService{

}