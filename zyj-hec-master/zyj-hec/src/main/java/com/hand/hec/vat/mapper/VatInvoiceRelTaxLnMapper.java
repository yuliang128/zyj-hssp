package com.hand.hec.vat.mapper;

import org.apache.ibatis.annotations.Param;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.vat.dto.VatInvoiceRelTaxLn;

public interface VatInvoiceRelTaxLnMapper extends Mapper<VatInvoiceRelTaxLn>{
    /**
     * <p>根据关联ID获取发票用途(可抵扣类)</p>
     *
     * @param releationId 发票关联ID
     * @param invoiceId 发票ID
     * @return Long
     * @author yang.duan 2019/6/11 13:48
     **/
    Long getInvoiceUsedeId(@Param("releationId") Long releationId, @Param("invoiceId") Long invoiceId);

    /**
     * <p>根据单据ID获取发票用途(可抵扣类)</p>
     *
     * @param expReportHeaderId 报销单头ID
     * @param expReportLineId 报销单行ID
     * @return Long
     * @author yang.duan 2019/6/11 14:02
     **/
    Long getInvoiceUsedeIdByDocId(@Param("expReportHeaderId") Long expReportHeaderId,@Param("expReportLineId") Long expReportLineId);
}