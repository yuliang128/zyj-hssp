package com.hand.hec.vat.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.DTOStatus;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.base.service.HecUtil;
import com.hand.hec.expm.dto.ExpReportHeader;
import com.hand.hec.expm.service.IExpReportHeaderService;
import com.hand.hec.vat.dto.VatInvoice;
import com.hand.hec.vat.dto.VatInvoiceLine;
import com.hand.hec.vat.service.IVatInvoiceLineService;
import com.hand.hec.vat.service.IVatInvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hand.hec.vat.dto.VatInvoiceRelationLn;
import com.hand.hec.vat.service.IVatInvoiceRelationLnService;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class VatInvoiceRelationLnServiceImpl extends BaseServiceImpl<VatInvoiceRelationLn> implements IVatInvoiceRelationLnService {

    @Autowired
    private IExpReportHeaderService expReportHeaderService;


    @Autowired
    private IVatInvoiceService vatInvoiceService;

    @Autowired
    private IVatInvoiceLineService vatInvoiceLineService;

    /**
     * <p>保存报销单与发票的关联关系</p>
     *
     * @param request
     * @param invoice           发票
     * @param expReportHeaderId 报销单头ID
     * @param expReportLineId   报销单行ID
     * @return VatInvoiceRelationLn
     * @author yang.duan 2019/6/14 10:54
     **/
    @Override
    public VatInvoiceRelationLn saveInvoiceRelationLns(IRequest request, VatInvoice invoice, Long expReportHeaderId, Long expReportLineId) {
        VatInvoiceRelationLn dto = new VatInvoiceRelationLn();
        VatInvoice vatInvoice = new VatInvoice();
        //1.0 初始化及判断参数【若：未录入发票代码/发票号码则默认为仅仅录入税】
        if (invoice != null && invoice.getInvoiceCode() != null && invoice.getInvoiceNumber() != null) {
            //1.0.1 若税率类型，税额也未录入，则直接跳出逻辑
            if (invoice.getTaxTypeId() == null && invoice.getTaxAmount() == null && invoice.getInvoiceAmount() == null) {
                return null;
            }
            if (invoice.getRelationFlag() == null || "Y".equals(invoice.getRelationFlag())) {
                dto.setRelationLnsId(invoice.getRelationLnsId());
                dto.setDocumentCategory(ExpReportHeader.EXP_REPORT);
                dto.setDocumentId(expReportHeaderId);
                dto.setDocumentLineId(expReportLineId);
                dto.setInvoiceId(invoice.getInvoiceId());
                dto.setInvoiceLineId(invoice.getInvoiceLineId());
                dto.setBusinessAmount(invoice.getBusinessAmount());
                dto.setBiz2payExchangeRate(invoice.getBiz2payExchangeRate());
                dto.setPaymentAmount(invoice.getPaymentAmount());
                dto.setPay2funExchangeRate(invoice.getPay2funExchangeRate());
                dto.setFunctionalAmount(invoice.getFunctionalAmount());
                dto.setTaxTypeId(invoice.getTaxTypeId());
                dto.setTaxAmount(BigDecimal.valueOf(invoice.getTaxAmount()));
                dto = saveInvoiceRelationLns(request, dto);
                return dto;
            }
        }
        //获取单据信息
        ExpReportHeader reportHeader = new ExpReportHeader();
        reportHeader.setExpReportHeaderId(expReportHeaderId);
        reportHeader = expReportHeaderService.selectByPrimaryKey(request, reportHeader);

        //2.0 创建发票（当为手工录入发票时候创建发票）
        if ("MANAUL".equals(invoice.getInvoiceType())) {
            //处理发票头
            if (invoice.getInvoiceId() != null) {
                vatInvoice.setInvoiceId(invoice.getInvoiceId());
                vatInvoice.setInvoiceCode(invoice.getInvoiceCode());
                vatInvoice.setInvoiceNumber(invoice.getInvoiceNumber());
                vatInvoice.setInvoiceDate(invoice.getInvoiceDate());
                vatInvoice.setAmount(invoice.getInvoiceAmount().longValue());
                vatInvoice.setAmountZhs(new HecUtil().amountConvertToChinese(invoice.getAmount().toString()));
                vatInvoice.setTaxAmount(invoice.getTaxAmount());
                vatInvoice.setWithoutTaxAmount(invoice.getInvoiceAmount().subtract(BigDecimal.valueOf(invoice.getTaxAmount() == null ? 0 : invoice.getTaxAmount())).longValue());
                vatInvoice.setInvoiceType(invoice.getInvoiceType());
                vatInvoice.setInvoiceCategoryId(invoice.getInvoiceCategoryId());
                vatInvoice = vatInvoiceService.updateVatInvoicePart(request, vatInvoice);
            } else {
                VatInvoice invoiceDto = new VatInvoice();
                invoiceDto.setInvoiceCode(invoice.getInvoiceCode());
                invoiceDto.setInvoiceNumber(invoice.getInvoiceNumber());
                List<VatInvoice> list = vatInvoiceService.select(request, invoiceDto, 1, 0);
                if (list != null && !list.isEmpty()) {
                    invoiceDto = list.get(0);
                }
                if (invoiceDto != null && invoiceDto.getInvoiceId() != null) {
                    vatInvoice.setInvoiceCode(invoice.getInvoiceCode());
                    vatInvoice.setInvoiceId(invoiceDto.getInvoiceId());
                    vatInvoice.setInvoiceNumber(invoice.getInvoiceNumber());
                    vatInvoice.setInvoiceDate(invoice.getInvoiceDate());
                    vatInvoice.setAmount(invoice.getInvoiceAmount().longValue());
                    vatInvoice.setAmountZhs(new HecUtil().amountConvertToChinese(invoice.getAmount().toString()));
                    vatInvoice.setTaxAmount(invoice.getTaxAmount());
                    vatInvoice.setWithoutTaxAmount(invoice.getInvoiceAmount().subtract(BigDecimal.valueOf(invoice.getTaxAmount() == null ? 0 : invoice.getTaxAmount())).longValue());
                    vatInvoice.setInvoiceType(invoice.getInvoiceType());
                    vatInvoice.setInvoiceCategoryId(invoice.getInvoiceCategoryId());
                    vatInvoice = vatInvoiceService.updateVatInvoicePart(request, vatInvoice);
                }else{
                    vatInvoice.setInvoiceCode(invoice.getInvoiceCode());
                    vatInvoice.setInvoiceNumber(invoice.getInvoiceNumber());
                    vatInvoice.setInvoiceDate(invoice.getInvoiceDate());
                    vatInvoice.setAmount(invoice.getInvoiceAmount().longValue());
                    vatInvoice.setTaxAmount(invoice.getTaxAmount());
                    vatInvoice.setInvoiceCategoryId(invoice.getInvoiceCategoryId());
                    vatInvoice.setEmployeeId(reportHeader.getEmployeeId());
                    vatInvoice.setAccEntityId(reportHeader.getAccEntityId());
                    vatInvoice = vatInvoiceService.generateInvoice(request, vatInvoice);
                }
            }
            //2.0.2 处理发票行（含发票税金行）
            if(invoice.getInvoiceLineId()!=null){
                VatInvoiceLine invoiceLine = new VatInvoiceLine();
                invoiceLine.setInvoiceId(vatInvoice.getInvoiceId());
                invoiceLine.setInvoiceLineId(invoice.getInvoiceLineId());
                invoiceLine.setWithoutTaxAmount(invoice.getInvoiceAmount().subtract(BigDecimal.valueOf(invoice.getTaxAmount()==null?0:invoice.getTaxAmount())));
                invoiceLine.setTaxTypeId(invoice.getTaxTypeId());
                invoiceLine.setTaxAmount(BigDecimal.valueOf(invoice.getTaxAmount()));
                invoiceLine = vatInvoiceLineService.updateVatInvoiceLinePart(request,invoiceLine);
            }else{
                VatInvoiceLine invoiceLine = new VatInvoiceLine();
                invoiceLine.setInvoiceId(vatInvoice.getInvoiceId());
                invoiceLine.setInvoiceLineId(invoice.getInvoiceLineId());
                invoiceLine.setWithoutTaxAmount(invoice.getInvoiceAmount().subtract(BigDecimal.valueOf(invoice.getTaxAmount()==null?0:invoice.getTaxAmount())));
                invoiceLine.setTaxTypeId(invoice.getTaxTypeId());
                invoiceLine.setTaxAmount(BigDecimal.valueOf(invoice.getTaxAmount()));
                invoiceLine = vatInvoiceLineService.generateInvoiceLine(request,invoiceLine);
            }
            //3.0 保存报销单行与发票的关联关系
            if("Y".equals(invoice.getRelationFlag()) || invoice.getRelationFlag()==null){
                dto.setRelationLnsId(invoice.getRelationLnsId());
                dto.setDocumentCategory(ExpReportHeader.EXP_REPORT);
                dto.setDocumentId(expReportHeaderId);
                dto.setDocumentLineId(expReportLineId);
                dto.setInvoiceId(invoice.getInvoiceId());
                dto.setInvoiceLineId(invoice.getInvoiceLineId());
                dto.setBusinessAmount(invoice.getBusinessAmount());
                dto.setBiz2payExchangeRate(invoice.getBiz2payExchangeRate());
                dto.setPaymentAmount(invoice.getPaymentAmount());
                dto.setPay2funExchangeRate(invoice.getPay2funExchangeRate());
                dto.setFunctionalAmount(invoice.getFunctionalAmount());
                dto.setTaxTypeId(invoice.getTaxTypeId());
                dto.setTaxAmount(BigDecimal.valueOf(invoice.getTaxAmount()));
                dto = saveInvoiceRelationLns(request, dto);
            }
        }
        return dto;
    }

    private VatInvoiceRelationLn saveInvoiceRelationLns(IRequest request, VatInvoiceRelationLn invoiceRelationLn) {
        if (invoiceRelationLn != null) {
            //保存发票关联关系
            if (invoiceRelationLn.getRelationLnsId() == null) {
                invoiceRelationLn = self().insertSelective(request, invoiceRelationLn);
            } else {
                invoiceRelationLn = self().updateByPrimaryKey(request, invoiceRelationLn);
            }
            //生成分配行关联关系和税金行(未)
        }
        return invoiceRelationLn;
    }
}