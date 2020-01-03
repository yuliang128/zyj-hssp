package com.hand.hec.expm.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.exp.dto.ExpMoExpenseItem;
import com.hand.hec.exp.dto.ExpMoExpenseType;
import com.hand.hec.expm.dto.ExpReportLine;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;


/**
 * <p>
 * ExpReportLineMapper
 * </p>
 *
 * @author yang.duan 2019/01/10 15:02
 */
public interface ExpReportLineMapper extends Mapper<ExpReportLine> {
    /**
     * 查询默认报销类型
     *
     * @author yang.cai 2019-3-6 9:54
     * @return 默认报销类型
     */
    List<ExpMoExpenseType> queryExpenseTypeDefault(@Param("moExpReportTypeId") Long moExpReportTypeId,
                    @Param("pageElementCode") String pageElementCode, @Param("companyId") Long companyId);

    /**
     * 查询报销类型
     * 
     * @param moExpReportTypeId
     * @param pageElementCode
     * @param companyId
     * @author yang.cai 2019-3-6 9:54
     * @return 报销类型
     */
    List<ExpMoExpenseType> queryExpenseType(@Param("moExpReportTypeId") Long moExpReportTypeId,
                    @Param("pageElementCode") String pageElementCode, @Param("companyId") Long companyId);


    /**
     * <p>查询费用项目</p>
     *
     * @param moExpReportTypeId
     * @param moExpenseTypeId
     * @param pageElementCode
     * @param companyId
     * @return  List<ExpMoExpenseItem>
     * @author yang.duan 2019/4/26 17:01
     **/
    List<ExpMoExpenseItem> queryExpenseItem(@Param("moExpReportTypeId") Long moExpReportTypeId,
                    @Param("moExpenseTypeId") Long moExpenseTypeId, @Param("pageElementCode") String pageElementCode,
                    @Param("companyId") Long companyId);

//    /**
//     * 查询税种代码
//     *
//     * @param expReportLine
//     * @author yang.cai 2019-3-6 9:54
//     * @return 税种代码
//     */
//    List<ExpReportLine> queryTaxTypeCode(ExpReportLine expReportLine);

    Long getMaxLineNUmber(@Param("expReportHeaderId") Long expReportHeaderId);

    /**
     * 单据审核批量更新行数据
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
     * 获取报销单页面属性类型
     * <p/>
     *
     * @param expReportHeaderId 报销单头ID
     * @return 页面属性类型
     * @author yang.duan 2019/3/14 11:22
     */
    String getReportPageElementCode(@Param("expReportHeaderId") Long expReportHeaderId);


    /**
     * <p>
     * 校验分配行金额与行金额是否相等
     * <p/>
     *
     * @param expReportHeaderId 报销单头ID
     * @return
     * @author yang.duan 2019/4/2 19:49
     */
    Long checkDisAmount(@Param("expReportHeaderId") Long expReportHeaderId);


    /**
     * <p>
     * 查询报销单行主数据
     * <p/>
     *
     * @param expReportHeaderId 报销单头ID
     * @param reportPageElementCode 页面元素类型code
     * @return 报销单行list
     * @author yang.duan 2019/4/3 13:22
     */
    List<ExpReportLine> reportLineQuery(@Param("expReportHeaderId") Long expReportHeaderId,
                    @Param("reportPageElementCode") String reportPageElementCode);

    void updateStatus(@Param("reportStatus") String reportStatus, @Param("userId") Long userId,
                    @Param("expReportHeaderId") Long expReportHeaderId);

    /**
     *获取报销单付款总金额
     *
     *@Author hui.zhao01@hand-china.com
     *@Date 2019/6/2 15:36
     *@param reportHeaderId
     *@return BigDecimal
     *@Version 1.0
     **/
    BigDecimal getTotalPaymentAmount(@Param("reportHeaderId") Long reportHeaderId);
}
