package com.hand.hec.vat.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.hand.hec.vat.dto.VatInvoiceRelTaxLn;
import com.hand.hec.vat.service.IVatInvoiceRelTaxLnService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class VatInvoiceRelTaxLnServiceImpl extends BaseServiceImpl<VatInvoiceRelTaxLn> implements IVatInvoiceRelTaxLnService{

}