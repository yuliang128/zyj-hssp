package com.hand.hec.cont.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.hand.hec.cont.dto.ConPaymentSchedule;
import com.hand.hec.cont.service.IConPaymentScheduleService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class ConPaymentScheduleServiceImpl extends BaseServiceImpl<ConPaymentSchedule> implements IConPaymentScheduleService{

}