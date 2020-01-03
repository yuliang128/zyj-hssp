package com.hand.hec.vat.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.fnd.dto.FndTaxTypeCode;
import com.hand.hec.fnd.service.IFndTaxTypeCodeService;
import com.hand.hec.vat.dto.VatInvoiceLine;
import com.hand.hec.vat.dto.VatInvoiceTaxLine;
import com.hand.hec.vat.service.IVatInvoiceLineService;
import com.hand.hec.vat.service.IVatInvoiceService;
import com.hand.hec.vat.service.IVatInvoiceTaxLineService;

@Service
@Transactional(rollbackFor = Exception.class)
public class VatInvoiceLineServiceImpl extends BaseServiceImpl<VatInvoiceLine> implements IVatInvoiceLineService {

    @Autowired
    private IVatInvoiceService vatInvoiceService;

    @Autowired
    private IFndTaxTypeCodeService fndTaxTypeCodeService;

    @Autowired
    private IVatInvoiceTaxLineService vatInvoiceTaxLineService;

    /**
     * <p>更新增值税发票行</p>
     *
     * @param request
     * @param invoiceLine 增值税发票行
     * @return VatInvoiceLine
     * @throws RuntimeException exception description
     * @author yang.duan 2019/6/17 14:36
     **/
    @Override
    public VatInvoiceLine updateVatInvoiceLinePart(IRequest request, VatInvoiceLine invoiceLine) {
        int invoiceTaxCount = 0;
        Long taxLineId = null;
        //1.0 校验状态
        if ("DOC_SUBMITTED".equals(vatInvoiceService.checkInvoiceStatus(request, invoiceLine.getInvoiceId()))) {
            throw new RuntimeException("该发票关联单据已被提交,不允许修改!");
        }
        //2.0 更新发票行
        VatInvoiceLine line = new VatInvoiceLine();
        line.setInvoiceLineId(invoiceLine.getInvoiceLineId());
        line.setWithoutTaxAmount(invoiceLine.getWithoutTaxAmount());
        line.setTaxAmount(invoiceLine.getTaxAmount());
        line.setTaxTypeId(invoiceLine.getTaxTypeId());
        FndTaxTypeCode taxTypeCode = new FndTaxTypeCode();
        taxTypeCode.setTaxTypeId(invoiceLine.getTaxTypeId());
        taxTypeCode = fndTaxTypeCodeService.selectByPrimaryKey(request, taxTypeCode);
        if (taxTypeCode != null) {
            line.setTaxRate(taxTypeCode.getTaxTypeRate());
        }
        line = self().updateByPrimaryKey(request, line);
        //3.0 获取值
        VatInvoiceTaxLine invoiceTaxLine = new VatInvoiceTaxLine();
        invoiceTaxLine.setInvoiceLineId(invoiceLine.getInvoiceLineId());
        List<VatInvoiceTaxLine> taxLineList = vatInvoiceTaxLineService.select(request, invoiceTaxLine, 1, 0);
        if (taxLineList != null && !taxLineList.isEmpty()) {
            invoiceTaxCount = taxLineList.size();
            taxLineList = taxLineList.stream().sorted((t1, t2) ->
                    t2.getTaxLineId().compareTo(t1.getTaxLineId())).collect(Collectors.toList());
            taxLineId = taxLineList.get(0).getTaxLineId();
        }
        //4.0 生成税金行
        if (invoiceTaxCount == 0 && invoiceLine.getTaxAmount() != null && invoiceLine.getTaxTypeId() != null) {
            VatInvoiceTaxLine taxLine = new VatInvoiceTaxLine();
            taxLine.setTaxLineId(taxLineId);
            taxLine.setInvoiceLineId(invoiceLine.getInvoiceLineId());
            taxLine.setTaxTypeId(invoiceLine.getTaxTypeId());
            taxLine.setTaxAmount(invoiceLine.getTaxAmount().longValue());
            taxLine.setTaxRate(taxTypeCode.getTaxTypeRate().longValue());
            taxLine = vatInvoiceTaxLineService.insertSelective(request, taxLine);
        }

        if (invoiceTaxCount == 1) {
            VatInvoiceTaxLine taxLine = new VatInvoiceTaxLine();
            taxLine.setTaxLineId(taxLineId);
            taxLine.setTaxTypeId(invoiceLine.getTaxTypeId());
            taxLine.setTaxAmount(invoiceLine.getTaxAmount().longValue());
            taxLine.setTaxRate(taxTypeCode.getTaxTypeRate().longValue());
            taxLine = vatInvoiceTaxLineService.updateByPrimaryKey(request,taxLine);
        }
        return line;
    }


    /**
     * <p>自动生成发票行</p>
     *
     * @param request
     * @param invoiceLine 增值税发票行
     * @return VatInvoiceLine
     * @throws RuntimeException exception description
     * @author yang.duan 2019/6/17 17:01
     **/
    @Override
    public VatInvoiceLine generateInvoiceLine(IRequest request,VatInvoiceLine invoiceLine){
        Long lineNumber = null;
        int lineCount = 0;
        VatInvoiceLine vatInvoiceLine = new VatInvoiceLine();
        //获取发票行号
        if(invoiceLine.getLineNumber()==null){
            VatInvoiceLine dto = new VatInvoiceLine();
            dto.setInvoiceId(invoiceLine.getInvoiceId());
            List<VatInvoiceLine> invoiceLineList = self().select(request,dto,1,0);
            if(invoiceLineList!=null && !invoiceLineList.isEmpty()){
                invoiceLineList = invoiceLineList.stream().sorted((t1,t2) ->
                        t2.getLineNumber().compareTo(t1.getLineNumber())).collect(Collectors.toList());
                lineNumber = (invoiceLineList.get(0).getLineNumber()==null?0:invoiceLineList.get(0).getLineNumber())+1;
            }
        }else{
            VatInvoiceLine dto = new VatInvoiceLine();
            dto.setInvoiceId(invoiceLine.getInvoiceId());
            dto.setLineNumber(invoiceLine.getLineNumber());
            List<VatInvoiceLine> invoiceLineList = self().select(request,dto,1,0);
            if(invoiceLineList!=null && !invoiceLineList.isEmpty()){
                lineCount = invoiceLineList.size();
                lineNumber = invoiceLine.getLineNumber();
            }
        }
        //若不存在发票行号、发票不含税金额，则录入发票行信息
        if(lineCount==0 && invoiceLine.getWithoutTaxAmount()!=null && invoiceLine.getTaxAmount()!=null){
            vatInvoiceLine.setInvoiceId(invoiceLine.getInvoiceId());
            vatInvoiceLine.setLineNumber(lineNumber);
            vatInvoiceLine.setQuantity(BigDecimal.ONE);
            vatInvoiceLine.setUnitPrice(invoiceLine.getWithoutTaxAmount());
            vatInvoiceLine.setWithoutTaxAmount(invoiceLine.getWithoutTaxAmount());
            vatInvoiceLine.setTaxTypeId(invoiceLine.getTaxTypeId());
            vatInvoiceLine.setTaxAmount(invoiceLine.getTaxAmount());
            FndTaxTypeCode taxTypeCode = new FndTaxTypeCode();
            taxTypeCode.setTaxTypeId(invoiceLine.getTaxTypeId());
            taxTypeCode = fndTaxTypeCodeService.selectByPrimaryKey(request, taxTypeCode);
            if (taxTypeCode != null) {
                vatInvoiceLine.setTaxRate(taxTypeCode.getTaxTypeRate());
            }
            vatInvoiceLine = self().insertVatInvoiceLine(request,vatInvoiceLine);
        }
        return vatInvoiceLine;
    }


    /**
     * <p>新增增值税发票行</p>
     *
     * @param request
     * @param invoiceLine 增值税发票行
     * @return VatInvoiceLine
     * @throws RuntimeException exception description
     * @author yang.duan 2019/6/17 18:07
     **/
    @Override
    public VatInvoiceLine insertVatInvoiceLine(IRequest request,VatInvoiceLine invoiceLine){
        Long lineNumber = null;
        //1.0 初始化
        VatInvoiceLine dto = new VatInvoiceLine();
        dto.setInvoiceId(invoiceLine.getInvoiceId());
        List<VatInvoiceLine> invoiceLineList = self().select(request,dto,1,0);
        if(invoiceLineList!=null && !invoiceLineList.isEmpty()){
            invoiceLineList = invoiceLineList.stream().sorted((t1,t2) ->
                    t2.getLineNumber().compareTo(t1.getLineNumber())).collect(Collectors.toList());
            lineNumber = (invoiceLineList.get(0).getLineNumber()==null?0:invoiceLineList.get(0).getLineNumber())+1;
        }
        invoiceLine.setLineNumber(lineNumber);
        invoiceLine = self().insertSelective(request,invoiceLine);
        //写入更多税额表
        VatInvoiceTaxLine taxLine = new VatInvoiceTaxLine();
        taxLine.setInvoiceLineId(invoiceLine.getInvoiceLineId());
        taxLine.setTaxTypeId(invoiceLine.getTaxTypeId());
        taxLine.setTaxRate(invoiceLine.getTaxRate().longValue());
        taxLine.setTaxAmount(invoiceLine.getTaxAmount().longValue());
        taxLine = vatInvoiceTaxLineService.insertSelective(request,taxLine);
        return invoiceLine;
    }
}