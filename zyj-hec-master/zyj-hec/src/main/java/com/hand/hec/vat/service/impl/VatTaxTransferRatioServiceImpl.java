package com.hand.hec.vat.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.hand.hec.vat.dto.VatTaxTransferRatio;
import com.hand.hec.vat.service.IVatTaxTransferRatioService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class VatTaxTransferRatioServiceImpl extends BaseServiceImpl<VatTaxTransferRatio> implements IVatTaxTransferRatioService{

}