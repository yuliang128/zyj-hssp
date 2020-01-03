package com.hand.hec.vat.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.hand.hec.vat.dto.VatInvoiceCategory;
import com.hand.hec.vat.service.IVatInvoiceCategoryService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class VatInvoiceCategoryServiceImpl extends BaseServiceImpl<VatInvoiceCategory> implements IVatInvoiceCategoryService{

}