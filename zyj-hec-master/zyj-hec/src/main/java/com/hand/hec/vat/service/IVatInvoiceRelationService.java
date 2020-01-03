package com.hand.hec.vat.service;

import java.util.Map;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.vat.dto.VatInvoiceRelation;

public interface IVatInvoiceRelationService extends IBaseService<VatInvoiceRelation>, ProxySelf<IVatInvoiceRelationService>{
    /**
     * <p>获取报销单当前关联的发票</p>
     *
     * @param request
     * @param expReportLineId 报销单行ID
     * @return Map
     * @author yang.duan 2019/6/10 16:56
     **/
    Map getExpRefInvoiceLine(IRequest request, Long expReportLineId);
}