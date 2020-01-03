package com.hand.hec.vat.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.vat.dto.VatInvoice;

public interface IVatInvoiceService extends IBaseService<VatInvoice>, ProxySelf<IVatInvoiceService>{
    /**
     * <p>部分更新增值税发票</p>
     *
     * @param request
     * @param invoice 发票DTO
     * @return VatInvoice
     * @throws RuntimeException exception description
     * @author yang.duan 2019/6/14 15:31
     **/
    VatInvoice updateVatInvoicePart(IRequest request, VatInvoice invoice) throws RuntimeException;


    /**
     * <p>快速生成发票，如果已经存在则直接返回发票ID</p>
     *
     * @param request
     * @param invoice 发票DTO
     * @return VatInvoice
     * @throws RuntimeException exception description
     * @author yang.duan 2019/6/17 10:14
     **/
    VatInvoice generateInvoice(IRequest request,VatInvoice invoice) throws RuntimeException;

    /**
     * <p>插入增值税发票</p>
     *
     * @param request
     * @param invoice 需要插入的invoiceDto
     * @return VatInvoice
     * @throws RuntimeException exception description
     * @author yang.duan 2019/6/17 13:42
     **/
    VatInvoice insertVatInvoice(IRequest request,VatInvoice invoice) throws RuntimeException;


    /**
     * <p>检查发票状态</p>
     *
     * @param request
     * @param invoiceId 发票ID
     * @return String
     * @author yang.duan 2019/6/14 15:35
     **/
    String checkInvoiceStatus(IRequest request, Long invoiceId);
}