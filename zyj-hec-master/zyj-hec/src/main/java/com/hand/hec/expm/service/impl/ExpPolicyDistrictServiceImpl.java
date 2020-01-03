package com.hand.hec.expm.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.hand.hec.expm.dto.ExpPolicyDistrict;
import com.hand.hec.expm.service.IExpPolicyDistrictService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class ExpPolicyDistrictServiceImpl extends BaseServiceImpl<ExpPolicyDistrict> implements IExpPolicyDistrictService{

}