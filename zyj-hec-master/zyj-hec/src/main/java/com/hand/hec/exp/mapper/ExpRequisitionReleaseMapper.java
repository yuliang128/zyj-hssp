package com.hand.hec.exp.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.exp.dto.ExpRequisitionRelease;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

public interface ExpRequisitionReleaseMapper extends Mapper<ExpRequisitionRelease> {

    List<Long> queryDocId(@Param("distId") long distId, @Param("headId") long headId);

    String queryAuditFlag(@Param("docId") long docId);

    /**
     * 查询报销单关联的申请单信息
     *
     * @param expReportHeaderId 报销单头Id
     * @param expReportLineId   报销单行Id
     * @param expReportDistId   报销单分配行Id
     * @return List<BgtBudgetReserve>
     * @Author hui.zhao01@hand-china.com
     * @Date 2019/3/14 11:20
     * @Version 1.0
     **/
    List<ExpRequisitionRelease> selectExpRequisitionReleaseInfo(@Param("expReportHeaderId") Long expReportHeaderId, @Param("expReportLineId") Long expReportLineId, @Param("expReportDistId") Long expReportDistId);

    /**
     * 获取对应的申请单已报销金额、已报销数量
     *
     * @param expRequisitionDistId
     * @return ExpRequisitionRelease
     * @Author hui.zhao01@hand-china.com
     * @Date 2019/3/18 10:55
     * @Version 1.0
     **/
    ExpRequisitionRelease getRequisitionDistsRelease(@Param("expRequisitionDistId") Long expRequisitionDistId);

    /**
     * <p>获取累计下达金额与数量</p>
     *
     * @param expRequisitionHeaderId
     * @param expRequisitionDistId
     * @return the return
     * @author yang.duan 2019/4/29 16:34
     **/
    ExpRequisitionRelease getTotalReleaseAmtQty(@Param("expRequisitionHeaderId") Long expRequisitionHeaderId, @Param("expRequisitionDistId") Long expRequisitionDistId);


    /**
     * <p>获取单据核销总金额</p>
     *
     * @param expRequisitionHeaderId 申请单头ID
     * @param expRequisitionDistId   申请单分配行ID
     * @return the return
     * @author yang.duan 2019/5/5 15:17
     **/
    BigDecimal getDocReleaseAmount(@Param("expRequisitionHeaderId") Long expRequisitionHeaderId, @Param("expRequisitionDistId") Long expRequisitionDistId, @Param("expReqDistsId") Long expReqDistsId);


    /**
     * <p>获取单据以前核销总金额</p>
     *
     * @param expRequisitionHeaderId 申请单头ID
     * @param expDocumentId          关联报销单头ID
     * @param expRequisitionDistId   申请单分配行ID
     * @return the return
     * @author yang.duan 2019/5/5 15:18
     **/
    BigDecimal getDocBeforeReleaseAmt(@Param("expRequisitionHeaderId") Long expRequisitionHeaderId, @Param("expDocumentId") Long expDocumentId, @Param("expRequisitionDistId") Long expRequisitionDistId);
}