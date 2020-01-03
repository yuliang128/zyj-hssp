package com.hand.hec.exp.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.hand.hec.exp.dto.ExpRequisitionTravelLine;
import com.hand.hec.exp.service.IExpRequisitionTravelLineService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class ExpRequisitionTravelLineServiceImpl extends BaseServiceImpl<ExpRequisitionTravelLine> implements IExpRequisitionTravelLineService{

}