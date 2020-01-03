package com.hand.hec.csh.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.acp.dto.AcpRequisitionLn;
import com.hand.hec.csh.dto.*;
import com.hand.hec.expm.dto.ExpReportHeader;
import com.hand.hec.expm.dto.ExpReportPmtSchedule;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 核销表Service
 * </p>
 *
 * @author Tagin 2019/01/22 09:51
 */
public interface ICshWriteOffService extends IBaseService<CshWriteOff>, ProxySelf<ICshWriteOffService> {

    /**
     * 报销单核销借款-单据信息-查询
     * 
     * @Author Tagin
     * @Date 2019/2/18 10:30
     * @Param paymentScheduleLineId 报销单计划付款行ID
     * @Version 1.0
     **/
    List<CshWriteOff> docQuery(IRequest iRequest, Long paymentScheduleLineId);

    /**
     * 报销单核销借款-未核销记录-查询
     * 
     * @Author Tagin
     * @Date 2019/1/29 10:30
     * @Param paymentScheduleLineId 报销单计划付款行ID
     * @Param requisitionNumber 单据编号
     * @Param dateFrom 日期从
     * @Param dateTo 日期到
     * @Version 1.0
     **/
    List<CshWriteOff> unWriteQuery(IRequest iRequest, Long paymentScheduleLineId, String requisitionNumber,
                    String dateFrom, String dateTo);

    /**
     * @Description 报销单核销借款-已核销记录-查询
     * @Author Tagin
     * @Date 2019/2/14 17:49
     * @Param paymentScheduleLineId 报销单计划付款行ID
     * @Version 1.0
     **/
    List<CshWriteOff> writeQuery(IRequest iRequest, Long paymentScheduleLineId);

    /**
     * 处理报销单核销状态
     * 
     * @Author Tagin
     * @Date 2019-02-21 12:23
     * @param iRequest 请求
     * @param expReportHeader 报销单头对象
     * @param expReportPmtSchedule 计划付款行对象
     * @param paymentFlag 是否为支付
     * @Return void
     * @Version 1.0
     **/
    void updateRptWriteStatus(IRequest iRequest, ExpReportHeader expReportHeader,
                    ExpReportPmtSchedule expReportPmtSchedule, String paymentFlag);

    /**
     * 更新借款单支付状态
     *
     * @param iRequest 请求
     * @param cshPaymentRequisitionLn 借款申请单行对象
     * @return void
     * @author Tagin
     * @date 2019-04-01 20:39
     * @version 1.0
     **/
    void updatePaymentStatus(IRequest iRequest, CshPaymentRequisitionLn cshPaymentRequisitionLn);

    /**
     * 更新付款申请单支付状态
     *
     * @param iRequest 请求
     * @param acpRequisitionLn 付款申请单行对象
     * @return void
     * @author Tagin
     * @date 2019-05-05 11:15
     * @version 1.0
     **/
    void updateAcpPaymentStatus(IRequest iRequest, AcpRequisitionLn acpRequisitionLn);

    /**
     * 报销单核销借款-核销记录-创建
     * 
     * @param iRequest 请求
     * @param cshWriteOffList 需创建核销记录集合
     * @Author Tagin
     * @Date 2019/2/19 15:59
     * @Version 1.0
     **/
    void createWrite(IRequest iRequest, List<CshWriteOff> cshWriteOffList);

    /**
     * 报销单核销借款-核销记录-删除
     *
     * @param iRequest
     * @param cshWriteOffList 待删除的核销集合
     * @Author Tagin
     * @Date 2019/2/18 17:49
     * @Version 1.0
     **/
    void deleteWrite(IRequest iRequest, List<CshWriteOff> cshWriteOffList);

    /**
     * 预付款核销明细信息
     *
     * @param transactionHeaderId
     * @param request
     * @param page
     * @param pageSize
     * @return java.util.List<java.util.Map>
     * @author ngls.luhui 2019-03-08 13:52
     */
    List<CshWriteOff> queryPrePayDetail(Long transactionHeaderId, IRequest request, Integer page, Integer pageSize);

    /**
     * 获取核销历史数据
     *
     * @param transactionHeaderId 现金事务头ID
     * @param request 上下文信息
     * @param page 页码
     * @param pageSize 页数
     * @return java.util.List<java.util.Map>
     * @author ngls.luhui 2019-03-12 14:58
     */
    List<Map> getWriteOffHistory(Long transactionHeaderId, IRequest request, Integer page, Integer pageSize);

    /**
     * 获取预付款核销的财务信息
     *
     * @param transactionHeaderId 现金事务头ID
     * @return
     * @author ngls.luhui 2019-03-13 19:05
     */
    List<Map> queryFinance(Long transactionHeaderId, IRequest request, Integer page, Integer pageSize);

    /**
     * 单据支付入口【含预付款核销】
     *
     * @author Tagin
     * @date 2019-03-28 11:45
     * @param iRequest 请求
     * @param cshDocPayment 单据支付基础信息
     * @param cshWriteOffs 核销集合
     * @param cshTransactionHeader 现金事务头对象
     * @param cshTransactionLine 现金事务行对象
     * @return void
     * @version 1.0
     **/
    void executePayment(IRequest iRequest, CshDocPayment cshDocPayment, List<CshWriteOff> cshWriteOffs,
                    CshTransactionHeader cshTransactionHeader, CshTransactionLine cshTransactionLine);


    String submitCshWriteOffCheck(IRequest request, String payeeCategory, Long payeeId, Long companyId,
                    Long accEntityId);

    /**
     * 处理现金事物退款核销
     *
     * @param rtnTrxHd
     * @param rtnTrxLn
     * @param sourceTrxHd
     * @param trxRtnList
     * @author mouse 2019-05-05 11:26
     * @return void
     */
    void returnCshTrxWriteOff(IRequest request, CshTransactionHeader rtnTrxHd, CshTransactionLine rtnTrxLn,
                    CshTransactionHeader sourceTrxHd, List<CshTransactionReturn> trxRtnList);

    /**
     * 报销单退款
     *
     * @param request
     * @param sourceWriteOff 原支付的核销记录
     * @param rtnTrxHd 退款现金事物头
     * @param rtnTrxLn 退款现金事物行
     * @param sourceTrxHd 原付款现金事物头
     * @param returnAmount 退款金额
     * @param functionalCurrencyCode 本币币种
     * @param setOfBooksId 账套ID
     * @author mouse 2019-05-05 16:11
     * @return void
     */
    void returnPaymentExpReport(IRequest request, CshWriteOff sourceWriteOff, CshTransactionHeader rtnTrxHd,
                    CshTransactionLine rtnTrxLn, CshTransactionHeader sourceTrxHd, BigDecimal returnAmount,
                    String functionalCurrencyCode, Long setOfBooksId);

    /**
     * 借款单退款
     *
     * @param request
     * @param sourceWriteOff 原支付的核销记录
     * @param rtnTrxHd 退款现金事物头
     * @param rtnTrxLn 退款现金事物行
     * @param sourceTrxHd 原付款现金事物头
     * @param returnAmount 退款金额
     * @param functionalCurrencyCode 本币币种
     * @param setOfBooksId 账套ID
     * @author mouse 2019-05-05 16:11
     * @return void
     */
    void returnPaymentPrepayment(IRequest request, CshWriteOff sourceWriteOff, CshTransactionHeader rtnTrxHd,
                    CshTransactionLine rtnTrxLn, CshTransactionHeader sourceTrxHd, BigDecimal returnAmount,
                    String functionalCurrencyCode, Long setOfBooksId);

    /**
     * 付款申请单提交校验 - 根据单据行ID查询已付款金额
     *
     * @param documentLineId
     * 
     * @author guiyuting 2019-05-06 16:22
     * 
     * @return
     */
    BigDecimal getPaidAmountByAcpReq(Long documentLineId);

    /**
     * 根据付款申请单行、现金事务获取核销数据
     *
     * @author Tagin
     * @date 2019-05-07 17:56
     * @param requisitionLnsId 付款申请单行 ID
     * @param transactionLineId 现金事务行 ID
     * @return com.hand.hec.csh.dto.CshWriteOff
     * @version 1.0
     **/
    CshWriteOff getWriteOffByAcpRef(Long requisitionLnsId, Long transactionLineId);


    /**
     * <p>
     * 核销信息查询(付款反冲)
     * </p>
     *
     * @param request
     * @param transactionHeaderId 现金事务头ID
     * @param pageNum
     * @param pageSize
     * @return List<Map>
     * @author yang.duan 2019/5/24 14:57
     **/
    List<Map> queryCshWriteOffHistory(IRequest request, Long transactionHeaderId, int pageNum, int pageSize);

    /**
     * 报销单支付反冲核销记录查询
     *
     * @author Tagin
     * @date 2019-05-28 16:22
     * @param transactionLineId 现金事务行对象
     * @param returnFlag 还款标志
     * @return java.util.List<com.hand.hec.csh.dto.CshWriteOff>
     * @version 1.0
     **/
    List<CshWriteOff> queryRptWriteForReverse(Long transactionLineId, String returnFlag);

    /**
     * 借款申请单支付反冲核销记录查询
     *
     * @author Tagin
     * @date 2019-05-28 16:56
     * @param transactionLineId 现金事务行 ID
     * @param transactionHeaderId 现金事务头 ID
     * @param sourceHeaderId 还款事务头 ID
     * @param returnFlag 还款标志
     * @return java.util.List<com.hand.hec.csh.dto.CshWriteOff>
     * @version 1.0
     **/
    List<CshWriteOff> queryPayWriteForReverse(Long transactionLineId, Long transactionHeaderId, Long sourceHeaderId,
                    String returnFlag);

    /**
     * 付款申请单支付反冲核销记录查询
     *
     * @author Tagin
     * @date 2019-05-28 16:58
     * @param transactionLineId 现金事务行 ID
     * @param returnFlag 还款标志
     * @return java.util.List<com.hand.hec.csh.dto.CshWriteOff>
     * @version 1.0
     **/
    List<CshWriteOff> queryAcpWriteForReverse(Long transactionLineId, String returnFlag);

	/**
	 * 预付款核销反冲核销记录查询
	 *
	 * @author LJK
	 * @date 2019-06-04 16:58
	 * @param request
	 * @param transactionHeaderId 现金事务头ID
	 * @param pageNum
	 * @param pageSize
	 * @return java.util.List<com.hand.hec.csh.dto.CshWriteOff>
	 * @version 1.0
	 **/
	List<CshWriteOff> queryPrePayWriteForReverse(IRequest request, Long transactionHeaderId, int pageNum, int pageSize);

	/**
	 * 预付款核销反冲
	 *
	 * @param request
	 * @param records 单据编号
	 * @return List<CshWriteOff>
	 * @author LJK 2019-06-04 16:16
	 */
	List<CshWriteOff> reversePrePayWrite(IRequest request, List<CshWriteOff> records);
}
