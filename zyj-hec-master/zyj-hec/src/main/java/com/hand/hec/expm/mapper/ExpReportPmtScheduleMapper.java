package com.hand.hec.expm.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.expm.dto.ExpReportPmtSchedule;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * ExpReportPmtScheduleMapper
 * </p>
 *
 * @author yang.duan 2019/01/10 15:03
 */
public interface ExpReportPmtScheduleMapper extends Mapper<ExpReportPmtSchedule> {

    /**
     * @Description 报销单计划付款行-报销总金额-查询
     * @Author Tagin
     * @Date 2019/2/18 17:49
     * @Param expReportHeaderId 报销单头ID
     * @Version 1.0
     **/
    BigDecimal totalAmount(@Param("expReportHeaderId") Long expReportHeaderId);

    /**
     * 付款申请单提交 - 根据单据行ID 查询总金额
     *
     * @param paymentScheduleLineId
     * @author guiyuting 2019-05-06 16:13
     * @return 
     */
    BigDecimal getTotalAmountByLine(@Param("paymentScheduleLineId") Long paymentScheduleLineId);

    /**
     * @Description 获取生成贷方凭证行方法
     * @Author hui.zhao01@hand-china.com
     * @Date 2019/2/18 9:57
     * @Param expReportHeaderId 报销单头ID
     * @Version 1.0
     **/
    List<ExpReportPmtSchedule> getExpReportPmtSchedules(@Param("expReportHeaderId") Long expReportHeaderId);

    /**
     * @Description 获取生成贷方税务凭证行方法
     * @Author hui.zhao01@hand-china.com
     * @Date 2019/2/18 9:57
     * @Param expReportHeaderId 报销单头ID
     * @Version 1.0
     **/
    List<ExpReportPmtSchedule> getExpReportPmtSchedulesTax(@Param("expReportHeaderId") Long expReportHeaderId);


    /**
     * <p>检查计划付款行是否发生过核销<p/>
     *
     * @param paymentScheduleLineId 计划付款行ID
     * @return 核销记录个数
     * @author yang.duan 2019/3/14 9:56
     */
    Long checkPmtScheduleLnOccurWf(@Param("paymentScheduleLineId") Long paymentScheduleLineId);


    List<ExpReportPmtSchedule> queryPmtSchedule(@Param("expReportHeaderId") Long expReportHeaderId);

    /**
     * <p>报销单核销借款只读页面头查询</p>
     *
     * @param paymentScheduleLineId 报销单计划付款行ID
     * @return List<Map>
     * @author yang.duan 2019/5/27 14:42
     **/
    List<Map>  writeOffPrepaymentHeaderQuery(@Param("paymentScheduleLineId") Long paymentScheduleLineId);


    /**
     * <p>报销单核销借款只读页面已核销记录查询</p>
     *
     * @param paymentScheduleLineId 计划付款行ID
     * @return the return
     * @author yang.duan 2019/5/27 15:26
    **/
    List<Map> writeOffPrepaymentHistoryQuery(@Param("paymentScheduleLineId") Long paymentScheduleLineId);
}
