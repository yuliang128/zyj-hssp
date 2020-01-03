package com.hand.hec.vat.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.vat.dto.VatInvoiceLine;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

public interface VatInvoiceLineMapper extends Mapper<VatInvoiceLine> {
    /**
     * <p>获取行累加价税合计金额</p>
     * 
     * @param expReportHeaderId 报销单头ID
     * @param expReportLineId 报销单行ID
     * @return BigDecimal
     * @author yang.duan 2019/6/11 14:25
    **/
    BigDecimal getInvoiceAmount(@Param("expReportHeaderId") Long expReportHeaderId, @Param("expReportLineId") Long expReportLineId);
}