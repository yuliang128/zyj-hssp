package com.hand.hec.expm.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.exp.dto.ExpMoReportType;
import com.hand.hec.expm.dto.ExpReportHeader;
import com.hand.hec.expm.dto.ExpReportLine;
import com.hand.hec.expm.dto.ExpReportPmtSchedule;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * IExpReportPmtScheduleService
 * </p>
 *
 * @author yang.duan 2019/01/10 15:07
 */
public interface IExpReportPmtScheduleService extends IBaseService<ExpReportPmtSchedule>, ProxySelf<IExpReportPmtScheduleService>{
    /**
     * <p>自动创建计划付款行<p/>
     *
     * @param request 请求
     * @param header 报销单头
     * @param lineList 报销单行list
     * @param reportType 报销单类型
     * @return 生成的计划付款行DTO
     * @throws RuntimeException exception
     * @author yang.duan 2019/3/13 13:54
     */
    ExpReportPmtSchedule createExpRepPmtSchedule(IRequest request, ExpReportHeader header, List<ExpReportLine> lineList, ExpMoReportType reportType) throws RuntimeException;

    /**
     * <p>新增计划付款行<p/>
     *
     * @param request 请求
     * @param expReportPmtSchedule 需要新增的计划付款行DTO
     * @param header 报销单头信息
     * @param reportType 报销单类型
     * @return 新增后的计划付款行DTO
     * @throws RuntimeException exception
     * @author yang.duan 2019/3/13 17:16
     */
    ExpReportPmtSchedule insertExpRepPmtSchedule(IRequest request, ExpReportPmtSchedule expReportPmtSchedule, ExpReportHeader header, ExpMoReportType reportType) throws RuntimeException;

    /**
     * <p>更新计划付款行<p/>
     *
     * @param request 请求
     * @param expReportPmtSchedule 需要更新的计划付款行DTO
     * @param header 报销单头信息
     * @param reportType 报销单类型
     * @return 更新后的计划付款行DTO
     * @throws RuntimeException exception
     * @author yang.duan 2019/3/13 17:16
     */
    ExpReportPmtSchedule updateExpRepPmtSchedule(IRequest request,ExpReportPmtSchedule expReportPmtSchedule, ExpReportHeader header, ExpMoReportType reportType) throws RuntimeException;


    /**
     * <p>检查计划付款行是否发生过核销<p/>
     *
     * @param paymentScheduleLineId 计划付款行ID
     * @return 核销记录个数
     * @author yang.duan 2019/3/14 9:56
     */
    Long checkPmtScheduleLnOccurWf(Long paymentScheduleLineId);

    /**
     * <p>删除报销单计划付款行<p/>
     *
     * @param request
     * @param dto 计划付款行dto
     * @return
     * @author yang.duan 2019/3/29 10:47
     */
    int deleteExpRepPmtSchedule(IRequest request,ExpReportPmtSchedule dto);

    List<ExpReportPmtSchedule> queryPmtSchedule(IRequest request,Long expReportHeaderId,int page,int pageSize);

    /**
     * 付款申请单提交 - 根据单据行ID 查询总金额
     *
     * @param paymentScheduleLineId
     * @author guiyuting 2019-05-06 16:13
     * @return
     */
    BigDecimal getTotalAmountByLine(Long paymentScheduleLineId);

    /**
     * <p>报销单核销借款只读页面头查询</p>
     *
     * @param request
     * @param paymentScheduleLineId 报销单计划付款行ID
     * @return List<Map>
     * @author yang.duan 2019/5/27 14:42
     **/
    List<Map>  writeOffPrepaymentHeaderQuery(IRequest request,Long paymentScheduleLineId);


    /**
     * <p>报销单核销借款只读页面已核销记录查询</p>
     *
     * @param request
     * @param paymentScheduleLineId 计划付款行ID
     * @param pageNum
     * @param pageSize
     * @return the return
     * @author yang.duan 2019/5/27 15:26
     **/
    List<Map> writeOffPrepaymentHistoryQuery(IRequest request, Long paymentScheduleLineId,int pageNum,int pageSize);
}