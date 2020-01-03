package com.hand.hec.vat.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.vat.dto.VatInvoiceRelation;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface VatInvoiceRelationMapper extends Mapper<VatInvoiceRelation>{
    /**
     *@Description 获取报销单对应发票信息
     *@Author hui.zhao01@hand-china.com
     *@Date 2019/2/18 11:03
     *@Param expReportHeaderId 报销单头ID
     *@Version 1.0
     **/
    List<VatInvoiceRelation> getVatInvoice(@Param("expReportHeaderId")Long expReportHeaderId);
    /**
     * <p>获取关联发票头（无行）/行数</p>
     *
     * @param expReportHeaderId 报销单头ID
     * @param expReportLineId 报销单行ID
     * @return Long
     * @author yang.duan 2019/6/10 17:10
     **/
    Long getVatCount(@Param("expReportHeaderId") Long expReportHeaderId,@Param("expReportLineId") Long expReportLineId);

    /**
     * <p>获取关联发票张数</p>
     *
     * @param expReportHeaderId 报销单头ID
     * @param expReportLineId 报销单行ID
     * @return VatInvoiceRelation
     * @author yang.duan 2019/6/10 17:23
     **/
    VatInvoiceRelation getVatRelationCount(@Param("expReportHeaderId") Long expReportHeaderId,@Param("expReportLineId") Long expReportLineId);

    /**
     * <p>获取关联发票行个数</p>
     *
     * @param expReportHeaderId 报销单头ID
     * @param expReportLineId 报销单行ID
     * @return the return
     * @author yang.duan 2019/6/10 19:49
     **/
    Long getVatLineCount(@Param("expReportHeaderId") Long expReportHeaderId,@Param("expReportLineId") Long expReportLineId);

    /**
     * <p>获取税金行个数</p>
     *
     * @param expReportLineId 报销单行ID
     * @return Long
     * @author yang.duan 2019/6/11 9:37
     **/
    Long getTaxCount(@Param("expReportLineId") Long expReportLineId);

    /**
     * <p>获取发票被报销单关联次数</p>
     *
     * @param invoiceId 发票ID
     * @return Long
     * @author yang.duan 2019/6/14 15:40
     **/
    Long getRelationCountByInvoiceId(@Param("invoiceId") Long invoiceId);
}