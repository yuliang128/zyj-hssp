package com.hand.hec.csh.service;

import java.util.Date;
import java.util.List;

import com.hand.hap.core.IRequest;
import com.hand.hec.csh.dto.CshTransactionHeader;
import com.hand.hec.csh.dto.CshTransactionLine;
import com.hand.hec.csh.dto.CshTransactionReturn;

/**
 * 现金事物处理程序
 *
 * @author mouse 2019/05/06 13:39
 */
public interface ICshTransactionService {

    /**
     * 调整凭证本位币尾差
     *
     * @param request
     * @param transactionLineId
     * @author mouse 2019-05-06 17:17
     * @return void
     */
    void setBalance(IRequest request, Long transactionLineId);

    /**
     * 更新预付款事物的退款状态
     *
     * @param request
     * @param header
     * @author mouse 2019-05-07 10:00
     * @return void
     */
    void setTrxReturnStatus(IRequest request, CshTransactionHeader header);

    /**
     * 获取预付款科目
     *
     *
     * @author mouse 2019-05-07 13:40
     * @return com.hand.hec.csh.dto.CshTransactionAccount
     */
    Long getPrepaymentAccount(IRequest request, CshTransactionHeader header, CshTransactionLine line);

    /**
     * 退款事物过账
     *
     *
     * @author mouse 2019-05-07 15:36
     * @return void
     */
    void postReturnTransaction(IRequest request, CshTransactionHeader rtnTrxHd, CshTransactionLine rtnTrxLn,
                    Long cashFlowItemId, List<CshTransactionReturn> trxRtnList);

    /**
     * 付款反冲
     *
     * @param iRequest 请求
     * @param cshTransactionHeader 现金事务头对象
     * @param cshTransactionLine 现金事务行对象
     * @param reversedDate 反冲日期
     * @return void
     * @author Tagin
     * @date 2019-05-28 10:05
     * @version 1.0
     **/
    void postReverseTransaction(IRequest iRequest, CshTransactionHeader cshTransactionHeader,
                    CshTransactionLine cshTransactionLine, Date reversedDate);

    /**
     *
     *
     * @param request
     * @param accEntityId
     * @param line
     * @param cashFlowItemId
     * @author mouse 2019-05-07 18:50
     * @return void
     */
    void createJournalCashFlow(IRequest request, Long accEntityId, CshTransactionLine line, Long cashFlowItemId);
}
