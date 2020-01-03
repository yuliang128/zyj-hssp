package com.hand.hec.gld.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.hand.hec.gld.dto.GldAccEntityTax;
import com.hand.hec.gld.service.IGldAccEntityTaxService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class GldAccEntityTaxServiceImpl extends BaseServiceImpl<GldAccEntityTax> implements IGldAccEntityTaxService{

}