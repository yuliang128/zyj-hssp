package com.hand.hec.expm.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.expm.dto.ExpReportDist;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * ExpReportDistMapper
 * </p>
 *
 * @author yang.duan 2019/01/10 15:01
 */
public interface ExpReportDistMapper extends Mapper<ExpReportDist> {
    /**
     * 获取生成借方凭证行数据方法
     *
     * @Author hui.zhao01@hand-china.com
     * @Date 2019/2/18 9:55
     * @param expReportHeaderId 报销单头ID
     * @return
     * @Version 1.0
     **/
    List<ExpReportDist> getExpReportDists(@Param("expReportHeaderId") Long expReportHeaderId);

    /**
     * 获取生成借方税务凭证行数据方法
     *
     * @Author hui.zhao01@hand-china.com
     * @Date 2019/2/18 9:55
     * @param expReportHeaderId 报销单头ID
     * @return
     * @Version 1.0
     **/
    List<ExpReportDist> getExpReportDistsTax(@Param("expReportHeaderId") Long expReportHeaderId);

    /**
     * 获取生成认证发票凭证行数据方法
     *
     * @Author hui.zhao01@hand-china.com
     * @Date 2019/2/18 9:55
     * @param expReportHeaderId 报销单头ID
     * @return
     * @Version 1.0
     **/
    List<ExpReportDist> getExpReportDistsCertTax(@Param("expReportHeaderId") Long expReportHeaderId);

    /**
     * 单据审核批量更新分配行数据
     *
     * @Author hui.zhao01@hand-china.com
     * @Date 2019/3/7 14:36
     * @param expReportHeaderId 报销单头Id
     * @param auditDate 审核日期
     * @param auditDateTime 带时区审核日期
     * @param auditFlag 审核标志
     * @return
     * @Version 1.0
     **/
    void batchUpdate(@Param("expReportHeaderId") Long expReportHeaderId, @Param("auditDate") Date auditDate,
                    @Param("auditDateTime") Timestamp auditDateTime, @Param("auditFlag") String auditFlag);

    /**
     * 单据审核拒绝批量更新行数据
     *
     * @Author hui.zhao01@hand-china.com
     * @Date 2019/3/7 14:36
     * @param expReportHeaderId 报销单头Id
     * @param reportStatus 单据状态
     * @param auditFlag 审核标志
     * @return
     * @Version 1.0
     **/
    void auditRejectBatchUpdate(@Param("expReportHeaderId") Long expReportHeaderId,
                    @Param("reportStatus") String reportStatus, @Param("auditFlag") String auditFlag);

    /**
     * <p>
     * 获取计划付款行总金额
     * <p/>
     *
     * @param expReportHeaderId 报销单头ID
     * @return 计划付款行总金额
     * @author yang.duan 2019/4/2 19:20
     */
    BigDecimal queryPaymentAmountByHeaderId(@Param("expReportHeaderId") Long expReportHeaderId);


    void updateStatus(@Param("reportStatus") String reportStatus, @Param("userId") Long userId,
                    @Param("expReportHeaderId") Long expReportHeaderId);
}
