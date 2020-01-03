package com.hand.hec.vat.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.util.BeanUtil;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.expm.dto.ExpReportHeader;
import com.hand.hec.expm.dto.ExpReportLine;
import com.hand.hec.expm.service.IExpReportLineService;
import com.hand.hec.vat.dto.VatInvoice;
import com.hand.hec.vat.dto.VatInvoiceLine;
import com.hand.hec.vat.dto.VatInvoiceRelTaxLn;
import com.hand.hec.vat.dto.VatInvoiceRelation;
import com.hand.hec.vat.mapper.VatInvoiceLineMapper;
import com.hand.hec.vat.mapper.VatInvoiceRelTaxLnMapper;
import com.hand.hec.vat.mapper.VatInvoiceRelationMapper;
import com.hand.hec.vat.service.IVatInvoiceLineService;
import com.hand.hec.vat.service.IVatInvoiceRelTaxLnService;
import com.hand.hec.vat.service.IVatInvoiceRelationService;
import com.hand.hec.vat.service.IVatInvoiceService;

@Service
@Transactional(rollbackFor = Exception.class)
public class VatInvoiceRelationServiceImpl extends BaseServiceImpl<VatInvoiceRelation> implements IVatInvoiceRelationService{
    @Autowired
    private VatInvoiceRelationMapper vatInvoiceRelationMapper;

    @Autowired
    private VatInvoiceRelTaxLnMapper vatInvoiceRelTaxLnMapper;

    @Autowired
    private VatInvoiceLineMapper vatInvoiceLineMapper;

    @Autowired
    private IExpReportLineService expReportLineService;

    @Autowired
    private IVatInvoiceRelTaxLnService vatInvoiceRelTaxLnService;

    @Autowired
    private IVatInvoiceService vatInvoiceService;

    @Autowired
    private IVatInvoiceLineService vatInvoiceLineService;

    @Autowired
    private IVatInvoiceRelationService vatInvoiceRelationService;


    /**
     * <p>获取报销单当前关联的发票</p>
     *
     * @param request
     * @param expReportLineId 报销单行ID
     * @return Map
     * @author yang.duan 2019/6/10 16:56
     **/
    @Override
    public Map getExpRefInvoiceLine(IRequest request, Long expReportLineId) {
        Long invoiceLineCount = Long.valueOf(-1);
        Long taxCount = Long.valueOf(-1);
        Long invoiceUsedeId = null;
        VatInvoice vatInvoice = new VatInvoice();
        VatInvoiceRelTaxLn relTaxLn = new VatInvoiceRelTaxLn();
        VatInvoiceRelation dto = new VatInvoiceRelation();
        //获取报销单行信息
        ExpReportLine line = new ExpReportLine();
        line.setExpReportLineId(expReportLineId);
        line = expReportLineService.selectByPrimaryKey(request, line);
        //1.0 获取关联发票头（无行）/行数
        Long vatAcount = vatInvoiceRelationMapper.getVatCount(line.getExpReportHeaderId(), expReportLineId);
        //2.0 获取关联发票张数
        VatInvoiceRelation invoiceRelation = vatInvoiceRelationMapper.getVatRelationCount(line.getExpReportHeaderId(), expReportLineId);
        //3.0 获取关联税额数
        //只关联了一个发票行或者一个无行发票
        if (Long.valueOf(1).compareTo(vatAcount) == 0) {
            invoiceLineCount = vatInvoiceRelationMapper.getVatLineCount(line.getExpReportHeaderId(), expReportLineId);
        }
        if (Long.valueOf(0).compareTo(invoiceLineCount) == 0) {
            dto.setTaxCount(Long.valueOf(-1));
        } else {
            taxCount = vatInvoiceRelationMapper.getTaxCount(expReportLineId);
            if (taxCount != null) {
                dto.setTaxCount(taxCount);
            }
        }
        //4.0 未关联任何发票信息
        if (Long.valueOf(0).compareTo(vatAcount) == 0) {
            dto.setDocumentCategory(ExpReportHeader.EXP_REPORT);
            dto.setDocumentId(line.getExpReportHeaderId());
            dto.setDocumentLineId(expReportLineId);
            dto.setVatCount(vatAcount);
            dto.setTaxCount(taxCount);
        }
        //5.0 只关联一张无行发票或一行发票
        if (Long.valueOf(1).compareTo(vatAcount) == 0) {
            //获取关联信息
            VatInvoiceRelation relation = new VatInvoiceRelation();
            relation.setDocumentCategory(ExpReportHeader.EXP_REPORT);
            relation.setDocumentId(line.getExpReportHeaderId());
            relation.setDocumentLineId(expReportLineId);
            List<VatInvoiceRelation> relations = self().select(request, relation, 1, 0);
            if (relations != null && !relations.isEmpty()) {
                relation = relations.get(0);
                relTaxLn.setRelationId(relation.getRelationId());
                List<VatInvoiceRelTaxLn> relTaxLns = vatInvoiceRelTaxLnService.select(request, relTaxLn, 1, 0);
                if (relTaxLns != null && !relTaxLns.isEmpty()) {
                    relTaxLn = relTaxLns.get(0);
                }
            }
            //5.0.2 若不关联发票【仅录入税】
            if (relation != null && relation.getInvoiceId() == null && relation.getInvoiceLineId() == null) {
                dto.setDocumentCategory(relation.getDocumentCategory());
                dto.setDocumentId(line.getExpReportHeaderId());
                dto.setDocumentLineId(expReportLineId);
                dto.setDocumentDistId(relation.getDocumentDistId());
                dto.setTaxAmount(BigDecimal.valueOf(relTaxLn.getSplittedTaxAmount()));
                dto.setTaxTypeId(relTaxLn.getTaxTypeId());
                dto.setRelationId(relation.getRelationId());
                dto.setVatCount(vatAcount);
                dto.setTaxCount(taxCount);
            }
            //5.0.3 若关联发票【仅关联发票头】
            if (relation != null && relation.getInvoiceId() != null && relation.getInvoiceLineId() == null) {
                //获取关联信息
                vatInvoice.setInvoiceId(relation.getInvoiceId());
                vatInvoice = vatInvoiceService.selectByPrimaryKey(request, vatInvoice);
                if (vatInvoice != null) {
                    dto.setDocumentCategory(relation.getDocumentCategory());
                    dto.setDocumentId(line.getExpReportHeaderId());
                    dto.setDocumentLineId(expReportLineId);
                    dto.setDocumentDistId(relation.getDocumentDistId());
                    dto.setInvoiceId(vatInvoice.getInvoiceId());
                    dto.setInvoiceCode(vatInvoice.getInvoiceCode());
                    dto.setInvoiceNumber(vatInvoice.getInvoiceNumber());
                    dto.setInvoiceDate(vatInvoice.getInvoiceDate());
                    dto.setInvoiceType(vatInvoice.getInvoiceType());
                    dto.setInvoiceSource(vatInvoice.getInvoiceSource());
                    dto.setInvoiceCategoryId(vatInvoice.getInvoiceCategoryId());
                    dto.setAmount(BigDecimal.valueOf(vatInvoice.getAmount()));
                    dto.setRelationId(relation.getRelationId());
                    dto.setVatCount(vatAcount);
                    dto.setTaxCount(taxCount);
                }
            }
            //5.0.4 若关联发票【关联发票行】
            if (relation != null && relation.getInvoiceId() != null && relation.getInvoiceLineId() != null) {
                //获取关联信息
                vatInvoice.setInvoiceId(relation.getInvoiceId());
                vatInvoice = vatInvoiceService.selectByPrimaryKey(request, vatInvoice);
                VatInvoiceLine invoiceLine = new VatInvoiceLine();
                invoiceLine.setInvoiceLineId(relation.getInvoiceLineId());
                invoiceLine = vatInvoiceLineService.selectByPrimaryKey(request, invoiceLine);
                //获取发票用途(可抵扣类发票)
                invoiceUsedeId = vatInvoiceRelTaxLnMapper.getInvoiceUsedeId(relation.getRelationId(), relation.getInvoiceId());
                dto.setDocumentCategory(relation.getDocumentCategory());
                dto.setDocumentId(line.getExpReportHeaderId());
                dto.setDocumentLineId(expReportLineId);
                dto.setDocumentDistId(relation.getDocumentDistId());
                dto.setInvoiceId(vatInvoice.getInvoiceId());
                dto.setInvoiceLineId(invoiceLine.getInvoiceLineId());
                dto.setInvoiceUsedeId(invoiceUsedeId);
                dto.setTaxAmount(invoiceLine.getTaxAmount());
                dto.setTaxTypeId(invoiceLine.getTaxTypeId());
                dto.setInvoiceCode(vatInvoice.getInvoiceCode());
                dto.setInvoiceNumber(vatInvoice.getInvoiceNumber());
                dto.setInvoiceDate(vatInvoice.getInvoiceDate());
                dto.setInvoiceType(vatInvoice.getInvoiceType());
                dto.setInvoiceSource(vatInvoice.getInvoiceSource());
                dto.setInvoiceCategoryId(vatInvoice.getInvoiceCategoryId());
                dto.setAmount(invoiceLine.getWithoutTaxAmount().add(invoiceLine.getTaxAmount()));
                dto.setRelationId(relation.getRelationId());
                dto.setVatCount(vatAcount);
                dto.setTaxCount(taxCount);
            }
        }
        //6.0 关联多行发票或多张无行发票
        if (Long.valueOf(1).compareTo(vatAcount) < 0) {
            //获取发票用途(关联发票行并且发票可抵扣)
            invoiceUsedeId = vatInvoiceRelTaxLnMapper.getInvoiceUsedeIdByDocId(line.getExpReportHeaderId(), line.getExpReportLineId());
            //关联同一张发票的不同行
            if (invoiceRelation != null && Long.valueOf(1).compareTo(invoiceRelation.getVatGCount()) == 0) {
                //获取关联信息
                VatInvoiceRelation relationDto = new VatInvoiceRelation();
                relationDto.setRelationId(invoiceRelation.getRelationId());
                relationDto = vatInvoiceRelationService.selectByPrimaryKey(request, relationDto);
                if (relationDto != null) {
                    vatInvoice.setInvoiceId(relationDto.getInvoiceId());
                    vatInvoice = vatInvoiceService.selectByPrimaryKey(request,vatInvoice);
                }
                //获取行累加价税合计金额
                BigDecimal amount = vatInvoiceLineMapper.getInvoiceAmount(line.getExpReportHeaderId(),line.getExpReportLineId());
                //写入变量
                dto.setDocumentCategory(relationDto.getDocumentCategory());
                dto.setDocumentId(relationDto.getDocumentId());
                dto.setDocumentLineId(relationDto.getDocumentLineId());
                dto.setDocumentDistId(relationDto.getDocumentDistId());
                dto.setInvoiceId(vatInvoice.getInvoiceId());
                dto.setInvoiceUsedeId(invoiceUsedeId);
                dto.setInvoiceCode(vatInvoice.getInvoiceCode());
                dto.setInvoiceNumber(vatInvoice.getInvoiceNumber());
                dto.setInvoiceDate(vatInvoice.getInvoiceDate());
                dto.setInvoiceType(vatInvoice.getInvoiceType());
                dto.setInvoiceSource(vatInvoice.getInvoiceSource());
                dto.setInvoiceCategoryId(vatInvoice.getInvoiceCategoryId());
                dto.setAmount(amount);
                dto.setVatCount(vatAcount);
                dto.setTaxCount(taxCount);

            } else {
                //关联不同发票的不同行
                dto.setDocumentCategory(ExpReportHeader.EXP_REPORT);
                dto.setDocumentId(line.getExpReportHeaderId());
                dto.setDocumentLineId(expReportLineId);
                dto.setVatCount(vatAcount);
                dto.setTaxCount(taxCount);
            }
        }
        return BeanUtil.convert2Map(dto);
    }
}