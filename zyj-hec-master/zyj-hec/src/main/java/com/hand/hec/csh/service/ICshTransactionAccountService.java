package com.hand.hec.csh.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.acp.dto.AcpRequisitionLn;
import com.hand.hec.csh.dto.*;
import com.hand.hec.expm.dto.ExpReportPmtSchedule;

import java.util.List;

/**
 * <p>
 * 现金事务凭证Service
 * </p>
 * 
 * @author Tagin 2019/01/22 20:05
 */
public interface ICshTransactionAccountService
                extends IBaseService<CshTransactionAccount>, ProxySelf<ICshTransactionAccountService> {

    /**
     * 费用报销单支付-生成支付凭证
     *
     * @author Tagin
     * @date 2019-03-22 15:26
     * @param iRequest 请求
     * @param cshDocPayment 支付基础对象
     * @param cshTransactionHeader 现金事务头对象
     * @param cshTransactionLine 现金事务行对象
     * @param cshWriteOff 核销对象
     * @param expReportPmtSchedule 报销单计划付款行对象
     * @return void
     * @version 1.0
     **/
    void generateReportAccount(IRequest iRequest, CshDocPayment cshDocPayment,
                    CshTransactionHeader cshTransactionHeader, CshTransactionLine cshTransactionLine,
                    CshWriteOff cshWriteOff, ExpReportPmtSchedule expReportPmtSchedule);

    /**
     * 借款申请单支付-生成支付凭证
     *
     * @author Tagin
     * @date 2019-03-29 17:14
     * @param iRequest 请求
     * @param cshDocPayment 支付基础对象
     * @param cshTransactionHeader 现金事务头对象
     * @param cshTransactionLine 现金事务行对象
     * @param cshWriteOff 核销对象
     * @param cshPaymentRequisitionLn 借款申请单行对象
     * @return void
     * @version 1.0
     **/
    void generateLoanAccount(IRequest iRequest, CshDocPayment cshDocPayment, CshTransactionHeader cshTransactionHeader,
                    CshTransactionLine cshTransactionLine, CshWriteOff cshWriteOff,
                    CshPaymentRequisitionLn cshPaymentRequisitionLn);

    /**
     * 付款申请单支付（零星付款）-生成支付凭证
     *
     * @author Tagin
     * @date 2019-05-05 10:43
     * @param iRequest 请求
     * @param cshDocPayment 支付基础对象
     * @param cshTransactionHeader 现金事务头对象
     * @param cshTransactionLine 现金事务行对象
     * @param cshWriteOff 核销对象
     * @param acpRequisitionLn 付款申请单行对象
     * @return void
     * @version 1.0
     **/
    void generateAcpAccount(IRequest iRequest, CshDocPayment cshDocPayment, CshTransactionHeader cshTransactionHeader,
                    CshTransactionLine cshTransactionLine, CshWriteOff cshWriteOff, AcpRequisitionLn acpRequisitionLn);

    /**
     * 付款申请单支付（预付付款）-生成支付凭证
     *
     * @author Tagin
     * @date 2019-05-05 10:43
     * @param iRequest 请求
     * @param cshDocPayment 支付基础对象
     * @param cshTransactionHeader 现金事务头对象
     * @param cshTransactionLine 现金事务行对象
     * @param cshWriteOff 核销对象
     * @param acpRequisitionLn 付款申请单行对象
     * @return void
     * @version 1.0
     **/
    void generateAcpPreAccount(IRequest iRequest, CshDocPayment cshDocPayment,
                    CshTransactionHeader cshTransactionHeader, CshTransactionLine cshTransactionLine,
                    CshWriteOff cshWriteOff, AcpRequisitionLn acpRequisitionLn);

    /**
     * 获取现金事务凭证
     *
     * @author Tagin
     * @date 2019-04-01 15:17
     * @param transactionLineId 现金事务行ID
     * @param usageCode 用途代码
     * @param drFlag 借方标识
     * @param crFlag 贷方标识
     * @param writeOffId 核销 ID
     * @return List<CshTransactionAccount>
     * @version 1.0
     **/
    CshTransactionAccount queryAccount(Long transactionLineId, String usageCode, String drFlag, String crFlag,
                    Long writeOffId);

    /**
     * 更新现金事物凭证的科目段
     *
     * @param request
     * @param account
     * @author mouse 2019-05-08 17:14
     * @return void
     */
    void updateAccountSegment(IRequest request, CshTransactionAccount account, String crDrFlag, Long setOfBooksId,
                    CshTransactionLine line, String docCategory, Long docHeaderId, Long docLineId, Long docDistId,
                    Long docPmtLineId);

    List<CshTransactionAccount> queryByLineId(IRequest request, Long transactionLineId, int page, int pageSize);

    /**
     * 还款登记单财务查询-根据头ID查询凭证
     * 
     * @param request
     * @param registerHdsId
     * @param page
     * @param pageSize
     * @author zhongyu 2019-05-17
     * @return
     */
    List<CshTransactionAccount> queryAccountForFinance(IRequest request, Long registerHdsId, int page, int pageSize);

    /**
     * 根据头ID查询凭证信息
     * 
     * @param request
     * @param transactionHeaderId
     * @param page
     * @param pageSize
     * @return
     */
    List<CshTransactionAccount> paymentFinanceInforQuery(IRequest request, Long transactionHeaderId, int page,
                    int pageSize);

}
