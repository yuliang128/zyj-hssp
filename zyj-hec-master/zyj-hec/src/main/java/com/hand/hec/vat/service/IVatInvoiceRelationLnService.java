package com.hand.hec.vat.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.vat.dto.VatInvoice;
import com.hand.hec.vat.dto.VatInvoiceRelationLn;

import javax.imageio.event.IIOReadProgressListener;

public interface IVatInvoiceRelationLnService extends IBaseService<VatInvoiceRelationLn>, ProxySelf<IVatInvoiceRelationLnService>{

    /**
     * <p>保存报销单与发票的关联关系</p>
     *
     * @param request
     * @param invoice 发票
     * @param expReportHeaderId 报销单头ID
     * @param expReportLineId 报销单行ID
     * @return VatInvoiceRelationLn
     * @author yang.duan 2019/6/14 10:54
    **/
    VatInvoiceRelationLn saveInvoiceRelationLns(IRequest request, VatInvoice invoice,Long expReportHeaderId,Long expReportLineId);
}