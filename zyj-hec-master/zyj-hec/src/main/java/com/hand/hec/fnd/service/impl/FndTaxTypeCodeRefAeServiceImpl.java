package com.hand.hec.fnd.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.hand.hec.fnd.dto.FndTaxTypeCodeRefAe;
import com.hand.hec.fnd.service.IFndTaxTypeCodeRefAeService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class FndTaxTypeCodeRefAeServiceImpl extends BaseServiceImpl<FndTaxTypeCodeRefAe> implements IFndTaxTypeCodeRefAeService{

}