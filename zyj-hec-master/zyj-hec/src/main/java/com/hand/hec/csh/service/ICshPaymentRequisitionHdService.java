package com.hand.hec.csh.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.csh.dto.CshPaymentRequisitionHd;
import com.hand.hec.csh.exception.CshPaymentRequisitionHdException;
import org.springframework.validation.BindingResult;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface ICshPaymentRequisitionHdService extends IBaseService<CshPaymentRequisitionHd>, ProxySelf<ICshPaymentRequisitionHdService>{
    /**
     *借款单头行信息保存
     *
     * @param   request IRequest
     * @param   dto CshPaymentRequisitionHd
     * @author dingwei.ma@hand-china.com 2019-01-22 10:41
     * @return List<CshPaymentRequisitionHd>
     */
    List<CshPaymentRequisitionHd> savePaymentRequisition(IRequest request, List<CshPaymentRequisitionHd> dto);

    /**
     * 借款单行信息更新
     *
     * @param request IRequest
     * @param dto CshPaymentRequisitionHd
     * @author dingwei.ma@hand-china.com 2019-01-22 15:13
     * @return
     */
    void updateByOptions(IRequest request, CshPaymentRequisitionHd dto);

    /**
     * 借款单提交校验
     *
     * @param request IRequest
     * @param dto CshPaymentRequisitionHd
     * @author dingwei.ma@hand-china.com 2019-01-22 15:13
     * @return
     */
    void submitCheck(IRequest request, CshPaymentRequisitionHd dto);

    /**
     * 借款申请单提交
     *
     * @param request IRequest
     * @param paymentRequisitionHeaderId Long
     * @author dingwei.ma@hand-china.com 2019-01-22 15:13
     * @return
     */
    void submitPaymentRequisition(IRequest request, Long paymentRequisitionHeaderId);

    /**
     * 借款申请单整单删除
     *
     * @param request required
     * @param paymentRequisitionHeaderId required
     * @author dingwei.ma@hand-china.com 2019-01-22 15:13
     * @return
     */
    void deletePaymentRequisition(IRequest request, Long paymentRequisitionHeaderId);

    /**
     * 借款申请单头状态更新
     *
     * @param request required
     * @param paymentRequisitionHeaderId required
     * @param approvalDate  Date
     * @param approvedBy  Long
     * @param closedDate   Date
     * @author dingwei.ma@hand-china.com 2019-01-22 15:13
     * @return
     */
    void updatePaymentRequisitionStatus(IRequest request, Long paymentRequisitionHeaderId,String status, Date approvalDate, Long approvedBy, Date closedDate);

    /**
     * 借款申请单审批通过
     *
	 * @param request
     * @param hdList  借款申请单实体
     * @author dingwei.ma@hand-china.com 2019-01-28 14:04
     * @return void
	 * @throws CshPaymentRequisitionHdException
     */
    void approvePaymentRequisition(IRequest request, List<CshPaymentRequisitionHd> hdList);

	/**
	 * 借款申请单拒绝
	 *
	 * @param request
	 * @param hdList  借款申请单实体
	 * @author LJK 2019-03-20 14:04
	 * @return void
	 */
	void reject(IRequest request, List<CshPaymentRequisitionHd> hdList);

    /**
     * 申请单提交调用  费用申请单审批则借款单自动提交
     *
     * @param expRequisitionHeaderId  Long
     * @param status  String
     * @author dingwei.ma@hand-china.com 2019-01-28 14:04
     * @return void
     */
    void submitPayReqWithReq(IRequest request, Long expRequisitionHeaderId, String status);

    /**
     * 变更借款单状态   申请单审批后调用
     *
     * @param expRequisitionHeaderId  Long
     * @param status  String
     * @author dingwei.ma@hand-china.com 2019-01-28 14:04
     * @return void
     */
    void setPayReqStatusWithReq(IRequest request, Long expRequisitionHeaderId, String status);

    /**
     * 变更借款单状态   申请单审批后调用
     *
     * @param request  IRequest
     * @param paymentRequisitionHeaderId  Long
     * @param paymentReqLineId  Long
     * @param cashFlowItemId  Long
     * @author dingwei.ma@hand-china.com 2019-01-28 14:04
     * @return void
     */
    void updatePayReqLnCash(IRequest request, Long paymentRequisitionHeaderId, Long paymentReqLineId, Long cashFlowItemId);

    /**
     * 借款单关闭
     *
     * @param request  IRequest
     * @param dto 借款单表头实体list
     * @author dingwei.ma@hand-china.com 2019-01-28 14:04
     * @return void
     */
    void closePaymentRequisition(IRequest request,List<CshPaymentRequisitionHd> dto);

	/**
	 * 借款申请单查询   关闭、审批时调用(dto中需状态参数)
	 *
	 * @param request  IRequest
	 * @param dto  借款申请单表头实体
	 * @param pageNum 页码
	 * @param pageSize 页数
	 * @author LJK 2019-02-25 14:04
	 * @return List<CshPaymentRequisitionHd>
	 */
	List<CshPaymentRequisitionHd>  queryPayReq(IRequest request, CshPaymentRequisitionHd dto,int pageNum, int pageSize);

    /**
     * 还款或退款完成关闭单据
     *
     * @param request  IRequest
     * @param paymentRequisitionHeaderId  Long
     * @param unReturnedAmount  BigDecimal
     * @param returnAmount  Long
     * @param refundType  String
     * @param closedDate  Date
     * @author dingwei.ma@hand-china.com 2019-01-28 14:04
     * @return void
     */
    void refundClosePaymentRequisition(IRequest request, Long paymentRequisitionHeaderId, BigDecimal unReturnedAmount, BigDecimal returnAmount, String refundType, Date closedDate) ;

    /**
     * 关闭借款单打开
     *
     * @param request  IRequest
     * @param paymentRequisitionHeaderId  Long
     * @author dingwei.ma@hand-china.com 2019-01-28 14:04
     * @return void
     */
    void openPaymentRequisition(IRequest request, Long paymentRequisitionHeaderId) ;

    /**
     * 借款单金额和申请单金额比较
     * 借款单>申请单 返回1  否则返回0
     *
     * @param request  IRequest
     * @param paymentReqLineType  String
     * @param expRequisitionHeaderId  Long
     * @author dingwei.ma@hand-china.com 2019-01-28 14:04
     * @return Long
     */
    Long checkRequisitionAmount(IRequest request, String paymentReqLineType, Long expRequisitionHeaderId) ;

    /**
     *变更单据状态为ssc 退回
     *
     * @param request  IRequest
     * @param paymentRequisitionHeaderId  Long
     * @param opinion  String
     * @author dingwei.ma@hand-china.com 2019-01-28 14:04
     * @return void
     */
    void updateSscReturnStatus(IRequest request, Long paymentRequisitionHeaderId, String opinion);

    /**
     *变更单据状态为ssc 退回
     *
     * @param request  IRequest
     * @param paymentRequisitionHeaderId  Long
     * @author dingwei.ma@hand-china.com 2019-01-28 14:04
     * @return void
     */
    void updateSscCancelReturnStatus(IRequest request, Long paymentRequisitionHeaderId);

    /**
     * 变更头上金额
     *
     * @param request  IRequest
     * @param paymentRequisitionHeaderId  Long
     * @author dingwei.ma@hand-china.com 2019-01-28 14:04
     * @return void
     */
    void updatePayReqHdAmount(IRequest request, Long paymentRequisitionHeaderId) ;

    /**
     * 变更头上描述
     *
     * @param request  IRequest
     * @param paymentRequisitionHeaderId  Long
     * @param description  String
     * @author dingwei.ma@hand-china.com 2019-01-28 14:04
     * @return void
     */
    void updatePayReqHdDesc(IRequest request, Long paymentRequisitionHeaderId,String description) ;

    /**
     * 变更行上账号和描述
     *
     * @param request  IRequest
     * @param paymentRequisitionHeaderId  Long
     * @param paymentRequisitionLineId  Long
     * @param accountNumber  String
     * @param description  String
     * @author dingwei.ma@hand-china.com 2019-01-28 14:04
     * @return void
     */
    void updatePayReqLnAccountNum(IRequest request, Long paymentRequisitionHeaderId, Long paymentRequisitionLineId,String accountNumber, String description) ;

    /**
     * 出纳/财务退回单据提交取消退回
     *
     * @param request  IRequest
     * @param paymentRequisitionHeaderId  Long
     * @author dingwei.ma@hand-china.com 2019-01-28 14:04
     * @return void
     */
    void cancelDocReturn(IRequest request, Long paymentRequisitionHeaderId);


	/**
	 * 借款申请单明细查询
	 *
	 * @param paymentRequisitionHeaderId 借款申请单表头id
	 * @param request  IRequest
	 * @author LJK 2019-02-22 18:21
	 * @return List<Map>
	 */
	List<Map> selectDetailByHdId(IRequest request,Long paymentRequisitionHeaderId);

    /**
     * 借款申请单财务查询
     *
     * @param request  上下文
     * @param allCompanyFlag  全公司标志
     * @param requisitionNumber 单据号
     * @param employeeId 员工Id
     * @param description 描述
     * @param requisitionDateFrom 申请日期从
     * @param requisitionDateTo 申请日期到
     * @param amountFrom 金额从
     * @param amountTo 金额到
     * @param payeeCategory 收款对象
     * @param payeeId 收款方
     * @param currencyCode 币种
     * @param status 状态
     * @param paymentMethodId 支付方式
     * @param cshPaymentRequisitionTypeId 单据类型
     * @param pageNum  页码
     * @param pageSize  页数
     * @author dingwei.ma@hand-china.com 2019-01-28 14:04
     * @return List<Map>
     */
    List<Map> queryForFinance(IRequest request, String allCompanyFlag, String requisitionNumber, Long employeeId
                                               ,String description, Date requisitionDateFrom, Date requisitionDateTo
                                               ,BigDecimal amountFrom, BigDecimal amountTo, String payeeCategory
                                               ,Long payeeId, String currencyCode, String status
                                               ,Long paymentMethodId, Long cshPaymentRequisitionTypeId, int pageNum, int pageSize);

    /**
     * 借款申请单财务查询
     *
     * @param request  上下文
     * @param requisitionNumber 单据号
     * @param employeeId 员工Id
     * @param requisitionDateFrom 申请日期从
     * @param requisitionDateTo 申请日期到
     * @param payeeCategory 收款对象
     * @param payeeId 收款方
     * @param currencyCode 币种
     * @param pageNum  页码
     * @param pageSize  页数
     * @author dingwei.ma@hand-china.com 2019-01-28 14:04
     * @return List<Map>
     */
    List<Map> queryForAudit(IRequest request, Long accEntityId, Long employeeId
                          , String requisitionNumber, Date requisitionDateFrom
                          , Date requisitionDateTo, String currencyCode
                          , String payeeCategory, Long payeeId, int pageNum, int pageSize);

	/**
	 * 获取期间名称
	 *
	 * @param date 日期
	 * @param request  IRequest
	 * @author LJK 2019-03-06 18:21
	 * @return List<Map>
	 */
	List<Map> getPeriodName(IRequest request,Date date);

	/**
	 * 借款申请单反冲
	 *
	 * @param request
	 * @param records 借款申请单头
	 * @author LJK 2019-03-06 18:21
	 * @return List<CshPaymentRequisitionHd>
	 * @throws CshPaymentRequisitionHdException
	 */
	List<CshPaymentRequisitionHd> reversePayReq(IRequest request, List<CshPaymentRequisitionHd> records);




	/**
	 * 费用申请财务关闭单查询   关闭、审批时调用(dto中需状态参数)
	 *
	 * @param request  IRequest
	 * @param dto  借款申请单表头实体
	 * @param pageNum 页码
	 * @param pageSize 页数
	 * @author weikun.wang2019-03-25 14:04
	 * @return List<CshPaymentRequisitionHd>
	 */
	List<CshPaymentRequisitionHd>  queryForRequisitionReverse(IRequest request, CshPaymentRequisitionHd dto,int pageNum, int pageSize);


	/**
	 * 我的借款申请查询
	 *
	 * @param request  IRequest
	 * @param dto 借款申请单表头实体
	 * @param pageNum 页码
	 * @param pageSize 页数
	 * @author LJK 2019-04-09 15:09
	 * @return List<Map>
	 */
	List<Map> queryPayRequisitionMain(IRequest request, CshPaymentRequisitionHd dto, int pageNum, int pageSize);

	/**
	 * 我的借款申请头查询
	 *
	 * @param request  IRequest
	 * @param paymentRequisitionHeaderId 借款申请单头id
	 * @param paymentReqTypeId 借款申请单头id
	 * @param employeeId 员工id
	 * @param accEntityId 核算主体id
	 * @author LJK 2019-04-09 14:05
	 * @return List<CshPaymentRequisitionHd>
	 */
	List<CshPaymentRequisitionHd> queryPaymentRequisitionHeader(IRequest request, Long paymentRequisitionHeaderId,
			Long paymentReqTypeId, Long employeeId, Long accEntityId);

	/**
	 * 借款申请单关联报销单查询
	 *
	 * @param paymentRequisitionHeaderId 借款申请单表头id
	 * @param request 上下文
	 * @author MDW 2019-02-22 18:21
	 * @return List<Map>
	 */
	List<Map> selectPayReqRefReport(IRequest request,Long paymentRequisitionHeaderId);

	/**
	 * 借款申请单关联还款单查询
	 *
	 * @param paymentRequisitionHeaderId 借款申请单表头id
	 * @param request 上下文
	 * @author MDW 2019-02-22 18:21
	 * @return List<Map>
	 */
	List<Map> selectPayReqRefRegister(IRequest request,Long paymentRequisitionHeaderId);

}