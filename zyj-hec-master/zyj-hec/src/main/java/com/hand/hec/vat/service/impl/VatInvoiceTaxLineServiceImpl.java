package com.hand.hec.vat.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.hand.hec.vat.dto.VatInvoiceTaxLine;
import com.hand.hec.vat.service.IVatInvoiceTaxLineService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class VatInvoiceTaxLineServiceImpl extends BaseServiceImpl<VatInvoiceTaxLine> implements IVatInvoiceTaxLineService{

}