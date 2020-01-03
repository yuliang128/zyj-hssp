package com.hand.hec.acc.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.hand.hec.acc.dto.AccJeTypeField;
import com.hand.hec.acc.service.IAccJeTypeFieldService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class AccJeTypeFieldServiceImpl extends BaseServiceImpl<AccJeTypeField> implements IAccJeTypeFieldService{

}