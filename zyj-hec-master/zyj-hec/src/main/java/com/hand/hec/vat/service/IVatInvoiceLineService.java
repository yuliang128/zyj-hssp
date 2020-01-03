package com.hand.hec.vat.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.vat.dto.VatInvoiceLine;

public interface IVatInvoiceLineService extends IBaseService<VatInvoiceLine>, ProxySelf<IVatInvoiceLineService>{
    
    /**
     * <p>更新增值税发票行</p>
     * 
     * @param request
     * @param invoiceLine 增值税发票行
     * @return VatInvoiceLine
     * @throws RuntimeException exception description
     * @author yang.duan 2019/6/17 14:36
    **/
    VatInvoiceLine updateVatInvoiceLinePart(IRequest request,VatInvoiceLine invoiceLine);
    
    
    /**
     * <p>自动生成发票行</p>
     * 
     * @param request
     * @param invoiceLine 增值税发票行
     * @return VatInvoiceLine
     * @throws RuntimeException exception description
     * @author yang.duan 2019/6/17 17:01
    **/
    VatInvoiceLine generateInvoiceLine(IRequest request,VatInvoiceLine invoiceLine);
    
    
    /**
     * <p>新增增值税发票行</p>
     * 
     * @param request
     * @param invoiceLine 增值税发票行
     * @return VatInvoiceLine
     * @throws RuntimeException exception description
     * @author yang.duan 2019/6/17 18:07
    **/
    VatInvoiceLine insertVatInvoiceLine(IRequest request,VatInvoiceLine invoiceLine);
}