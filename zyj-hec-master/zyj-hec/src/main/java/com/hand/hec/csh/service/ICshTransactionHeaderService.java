package com.hand.hec.csh.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.acp.dto.AcpRequisitionAccount;
import com.hand.hec.acp.dto.AcpRequisitionLn;
import com.hand.hec.csh.dto.*;
import com.hand.hec.expm.dto.ExpReportAccount;
import com.hand.hec.expm.dto.ExpReportPmtSchedule;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 现金事务头表Service
 * </p>
 *
 * @author Tagin 2019/01/22 10:30
 */
public interface ICshTransactionHeaderService
                extends IBaseService<CshTransactionHeader>, ProxySelf<ICshTransactionHeaderService> {

    /**
     * 获取报销单费用凭证-贷方凭证
     * 
     * @Author Tagin
     * @Date 2019-02-21 20:09
     * @param expReportPmtSchedule 费用报销单计划付款行对象
     * @Return com.hand.hec.expm.dto.ExpReportAccount
     * @Version 1.0
     **/
    ExpReportAccount getReportCrAccount(ExpReportPmtSchedule expReportPmtSchedule);


    /**
     * 获取借款申请单凭证【审核凭证/支付凭证】- 借方凭证
     *
     * @Author Tagin
     * @Date 2019-02-22 14:12
     * @param cshTransactionLine 预付款现金事务行对象
     * @param auitFlag 自审核标志
     * @Return com.hand.hec.csh.dto.CshPaymentReqAccount
     * @Version 1.0
     **/
    CshPaymentReqAccount getPaymentDrAccount(CshTransactionLine cshTransactionLine, String auitFlag);

    /**
     * 获取借款申请单凭证【审核凭证/支付凭证】- 贷方凭证
     *
     * @author Tagin
     * @date 2019-04-01 11:20
     * @param cshTransactionLine 现金事务行对象
     * @return com.hand.hec.csh.dto.CshPaymentReqAccount
     * @version 1.0
     **/
    CshPaymentReqAccount getPaymentCrAccount(CshTransactionLine cshTransactionLine);

    /**
     * 获取付款申请单审核凭证-借方凭证
     *
     * @author Tagin
     * @date 2019-04-30 15:34
     * @param acpRequisitionLn 付款申请单行对象
     * @return com.hand.hec.acp.dto.AcpRequisitionAccount
     * @version 1.0
     **/
    AcpRequisitionAccount getAcpRequisitionDrAccount(AcpRequisitionLn acpRequisitionLn);

    /**
     * 获取用途代码付款单科目 [PAY_REQUISITION]
     *
     * @author Tagin
     * @date 2019-05-06 20:51
     * @param iRequest 请求
     * @param transactionLineId 现金事务行 ID
     * @return java.lang.Long
     * @version 1.0
     **/
    Long getPayRequisitionAccount(IRequest iRequest, Long transactionLineId);

    /**
     * 获取用途代码预付款科目 [PREPAYMENT]
     *
     * @author Tagin
     * @date 2019-05-06 20:51
     * @param iRequest 请求
     * @param transactionLineId 现金事务行 ID
     * @return java.lang.Long
     * @version 1.0
     **/
    Long getPrepaymentAccount(IRequest iRequest, Long transactionLineId);

    /**
     * 获取用途代码现金内部往来应收科目 [CSH_INTERCOMPANY_AR]
     *
     * @author Tagin
     * @date 2019-05-07 15:18
     * @param iRequest 请求
     * @param companyId 公司 ID
     * @param accEntityId 核算主体 ID
     * @param oppositeAccEntityId 对方核算主体 ID
     * @param respCenterId 成本中心 ID
     * @param positionId 岗位 ID
     * @param currencyCode 币种
     * @param setOfBooksId 账套 ID
     * @return java.lang.Long
     * @version 1.0
     **/
    Long getCshIntercompanyArAccount(IRequest iRequest, Long companyId, Long accEntityId, Long oppositeAccEntityId,
                    Long respCenterId, Long positionId, String currencyCode, Long setOfBooksId);

    /**
     * 获取用途代码现金内部往来应收科目 [CSH_INTERCOMPANY_AP]
     *
     * @author Tagin
     * @date 2019-05-07 15:18
     * @param iRequest 请求
     * @param companyId 公司 ID
     * @param accEntityId 核算主体 ID
     * @param oppositeAccEntityId 对方核算主体 ID
     * @param respCenterId 成本中心 ID
     * @param positionId 岗位 ID
     * @param currencyCode 币种
     * @param setOfBooksId 账套 ID
     * @return java.lang.Long
     * @version 1.0
     **/
    Long getCshIntercompanyApAccount(IRequest iRequest, Long companyId, Long accEntityId, Long oppositeAccEntityId,
                    Long respCenterId, Long positionId, String currencyCode, Long setOfBooksId);

    /**
     * 获取用途代码汇兑损益科目 [REVALUATION]
     *
     * @author Tagin
     * @date 2019-05-07 15:37
     * @param iRequest 请求
     * @param companyId 公司 ID
     * @param accEntityId 核算主体 ID
     * @param respCenterId 成本中心 ID
     * @param positionId 岗位 ID
     * @param setOfBooksId 账套 ID
     * @param currencyCode 币种
     * @return java.lang.Long
     * @version 1.0
     **/
    Long getRevaluationAccount(IRequest iRequest, Long companyId, Long accEntityId, Long respCenterId, Long positionId,
                    Long setOfBooksId, String currencyCode);

    /**
     * 借款申请单查询交易信息
     *
     * @param request 上下文
     * @param headerId 头Id
     * @param pageNum 页码
     * @param pageSize 页数
     * @Author dingwei.ma@hand-china.com
     * @Date 2019-02-22 14:12
     * @Return List<Map>
     * @Version 1.0
     **/
    List<Map> queryByPayReqHeaderId(IRequest request, Long headerId, int pageNum, int pageSize);

    /**
     * 借款申请单查询交易信息
     *
     * @param request 上下文
     * @param headerId 头Id
     * @param pageNum 页码
     * @param pageSize 页数
     * @Author dingwei.ma@hand-china.com
     * @Date 2019-02-22 14:12
     * @Return List<Map>
     * @Version 1.0
     **/
    List<Map> queryWriteOffByPaYReqHeaderId(IRequest request, Long headerId, int pageNum, int pageSize);

    /**
     * 预付款核销页面信息
     *
     * @param request 上下文
     * @param pageNum 页码
     * @param pageSize 页数
     * @param accEntityId 核算主体ID
     * @param payeeCategory 收款方类别(EMPLOYEE,VENDER,CUSTOMER)
     * @param payeeId 收款方ID
     * @param currencyCode 币种
     * @param transactionNum 现金事务编号
     * @param transactionDateFrom 付款日期从
     * @param transactionDateTo 付款日期到
     * @param paymentMethodId 付款方式ID
     * @param userName 用户名
     * @param agentEmployeeId 代理员工
     * @param contactNumber 合同编号
     * @param amountFrom 付款金额从
     * @param amountTo 付款金额到
     * @param requisitionNumber 单据编号
     * @param bankAccountId 银行账号
     * @return java.util.List<java.util.Map>
     * @author ngls.luhui 2019-03-06 16:16
     */
    List<Map> queryPrePay(IRequest request, int pageNum, int pageSize, Long accEntityId, String payeeCategory,
                    Long payeeId, String currencyCode, String transactionNum, String transactionDateFrom,
                    String transactionDateTo, Long paymentMethodId, String userName, Long agentEmployeeId,
                    String contactNumber, String amountFrom, String amountTo, String requisitionNumber,
                    Long bankAccountId);

    /**
     * 查询预付款核销过账页面的头信息
     *
     * @param request 上下文环境
     * @param transactionHeaderId 现金事务头ID
     * @return List<Map> 过账页面的头信息
     * @author ngls.luhui 2019-03-07 19:44
     */
    List<Map> queryPrePayWtfByHId(IRequest request, Long transactionHeaderId);


    /**
     * 现金事务过账
     *
     * @author ngls.luhui 2019-03-08 16:25
     * @param iRequest 请求
     * @param transactionHeaderId 现金事务头ID
     * @param cshDocPayment 支付信息基础对象【Tips：此对象在预付款核销时为空】
     * @param cshWriteOffList 核销集合
     * @return void
     * @version 1.0
     */
    void postTransaction(IRequest iRequest, Long transactionHeaderId, CshDocPayment cshDocPayment,
                    List<CshWriteOff> cshWriteOffList);

    /**
     * 现金事务头录入
     *
     * @Author Tagin
     * @Date 2019-03-19 19:53
     * @param iRequest 请求
     * @param transactionType 现金事物类型(PAYMENT,PREPAYMENT)
     * @param moCshTrxClassId 现金事物分类ID
     * @param companyId 公司ID
     * @param accEntityId 核算主体ID
     * @param employeeId 经办人，源单据头上员工
     * @param description 描述
     * @param enabledWriteOffFlag 是否启用核销
     * @param transactionDate 交易日期
     * @param periodName 期间
     * @param paymentMethodId 付款方式ID
     * @param transactionCategory 现金事物类别(BUSINESS,MISCELLANEOUS)
     * @param postedFlag 过账标志
     * @param reversedFlag 反冲标志
     * @param reversedDate 反冲日期
     * @param returnedFlag 退款标志(R退款事物\Y部分退款\C完全退款\N未退款)
     * @param writeOffFlag 核销标志（C完全核销\Y部分核销\N未核销）
     * @param writeOffCompletedDate 核销日期
     * @param sourceHeaderId 反冲、退款的来源现金事物头ID
     * @param gldInterfaceFlag 总账标志（N未入总账\P可入总账）
     * @param sourcePaymentHeaderId 来源付款现金事物ID
     * @param ebankingFlag 网银标志
     * @param documentCategory 单据类别
     * @param documentTypeId 单据类型ID
     * @param paymentUsedeId 付款用途ID
     * @param payeeCategory 收款方类型
     * @param payeeId 收款方
     * @param currencyCode 币种
     * @param bankAccountId 付款方银行账户
     * @Return com.hand.hec.csh.dto.CshTransactionHeader
     * @Version 1.0
     **/
    CshTransactionHeader insertTrxHeader(IRequest iRequest, String transactionType, Long moCshTrxClassId,
                    Long companyId, Long accEntityId, Long employeeId, String description, String enabledWriteOffFlag,
                    Date transactionDate, String periodName, Long paymentMethodId, String transactionCategory,
                    String postedFlag, String reversedFlag, Date reversedDate, String returnedFlag, String writeOffFlag,
                    Date writeOffCompletedDate, Long sourceHeaderId, String gldInterfaceFlag,
                    Long sourcePaymentHeaderId, String ebankingFlag, String documentCategory, Long documentTypeId,
                    Long paymentUsedeId, String payeeCategory, Long payeeId, String currencyCode, Long bankAccountId);

    /**
     * 获取预付款已退款金额
     *
     * @param sourceHeaderId
     * @author mouse 2019-05-05 15:12
     * @return java.math.BigDecimal
     */
    BigDecimal getPrepaymentReturnedAmount(IRequest request, Long sourceHeaderId);


    /**
     * <p>付款反冲主页查询</p>
     *
     * @param  request
     * @param  pageNum
     * @param  pageSize
     * @param  docNumber 单据编号
     * @param  accEntityId 核算主体ID
     * @param  payeeCategory 收款方类别
     * @param  payeeId 收款方ID
     * @param  currencyCode 币种
     * @param  transactionNum 现金事务编号
     * @param  transactionDateFrom 现金事务日期从
     * @param  transactionDateTo 现金事务日期到
     * @param  paymentMethodId 付款方式ID
     * @param  paymentEmployeeName 支付员工名称
     * @param  agentEmployeeName 代理员工名称
     * @param  transactionAmountFrom 付款金额从
     * @param  transactionAmountTo 付款金额到
     * @return List<Map>
     * @author yang.duan 2019/5/23 10:41
     **/
    List<Map> queryPaymentReverse(IRequest request,int pageNum, int pageSize,String docNumber,Long accEntityId, String payeeCategory,
            Long payeeId, String currencyCode, String transactionNum, String transactionDateFrom, String transactionDateTo,
            Long paymentMethodId, String paymentEmployeeName, String agentEmployeeName, String transactionAmountFrom, String transactionAmountTo);


    /**
     * <p>付款反冲明细页面付款信息查询</p>
     *
     * @param request
     * @param transactionHeaderId 现金事务头ID
     * @return List<Map>
     * @author yang.duan 2019/5/24 10:35
     **/
    List<Map> cshTransactionInfoQuery(IRequest request, Long transactionHeaderId);
    
    /**
     * <p>根据现金事务获取单据编号</p>
     *
     * @param request
     * @param transactionHeaderId 现金事务头ID
     * @return String
     * @author yang.duan 2019/5/24 15:02
    **/
    String getDocNumber(IRequest request,Long transactionHeaderId);

    /**
     * <p>根据现金事务获取单据ID</p>
     *
     * @param request
     * @param transactionHeaderId 现金事务头ID
     * @return the return
     * @author yang.duan 2019/5/24 15:03
    **/
    Long getDocId(IRequest request,Long transactionHeaderId);



    /**
     * <p>付款查询页面信息</p>
     *
     * @param request
     * @param accEntityId 核算主体ID
     * @param agentEmployeeName 代理员工名称
     * @param bankAccountId 银行账户ID
     * @param contactNumber 合同编号
     * @param currencyCode  币种
     * @param docNumber 单据编号
     * @param docCategory 单据类别CODE
     * @param transactionAmountFrom 交易金额从
     * @param transactionAmountTo 交易金额到
     * @param payeeCategory 收款方类别
     * @param payeeId 收款方ID
     * @param paymentEmployeeName 付款员工
     * @param paymentMethodId 付款方式ID
     * @param sourceTransactionNum 来源现金事务编号
     * @param transactionDateFrom 交易日期从
     * @param transactionDateTo 交易日期到
     * @param transactionNum 现金事务编号
     * @param transactionType 现金事务类型
     * @param pageNum
     * @param pageSize
     * @return List<Map>
     * @author yang.duan 2019/5/29 16:19
     **/
    List<Map> cshTransactionQuery(IRequest request,Long accEntityId, String transactionDateFrom,
            String transactionDateTo, String payeeCategory, Long payeeId,
            String currencyCode,String transactionNum,
            Long bankAccountId,Long paymentMethodId,
            String paymentEmployeeName,String agentEmployeeName,
            String contactNumber,String transactionType,
            String transactionAmountFrom,String transactionAmountTo,
            String docNumber,String docCategory,String sourceTransactionNum,int pageNum,int pageSize);

    /**
     * <p>付款反冲</p>
     *
     * @param request
     * @param dto
     * @return void
     * @author yang.duan 2019/5/30 18:02
    **/
    void postReverseTransaction(IRequest request,List<CshTransactionHeader> dto);


	/**
	 * 预付款核销反冲查询
	 *
	 * @param request
	 * @param pageNum 页码
	 * @param pageSize 页数
	 * @param payeeCategory 收款方类别(EMPLOYEE,VENDER,CUSTOMER)
	 * @param payeeId 收款方ID
	 * @param currencyCode 币种
	 * @param transactionNum 现金事务编号
	 * @param transactionDateFrom 付款日期从
	 * @param transactionDateTo 付款日期到
	 * @param requisitionNumber 单据编号
	 * @return java.util.List<java.util.Map>
	 * @author LJK 2019-06-04 16:16
	 */
	List<Map> queryPrePayForReverse(IRequest request, String payeeCategory, Long payeeId, String currencyCode,
			String transactionNum, String transactionDateFrom, String transactionDateTo, String requisitionNumber,
			String contractNumber, int pageNum, int pageSize);

	/**
	 * <p>借款单财务查询页面付款信息查询</p>
	 *
	 * @param request
	 * @param transactionHeaderId 现金事务头ID
	 * @return List<Map>
	 * @author LJK 2019/6/13 10:35
	 **/
	List<Map> cshTransInfoQuery(IRequest request, Long transactionHeaderId);
}
