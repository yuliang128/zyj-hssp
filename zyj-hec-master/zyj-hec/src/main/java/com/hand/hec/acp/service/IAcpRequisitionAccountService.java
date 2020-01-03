package com.hand.hec.acp.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.acp.dto.AcpReqCurrencyTemp;
import com.hand.hec.acp.dto.AcpRequisitionAccount;
import com.hand.hec.acp.dto.AcpRequisitionHd;
import com.hand.hec.acp.exception.AcpRequisitionException;
import com.hand.hec.acp.dto.AcpRequisitionAccountTemp;
import java.util.Date;
import java.util.List;

public interface IAcpRequisitionAccountService
                extends IBaseService<AcpRequisitionAccount>, ProxySelf<IAcpRequisitionAccountService> {


    /**
     * 获取借款审核凭证
     *
     * @author Tagin
     * @date 2019-04-30 15:51
     * @param transactionLineId 现金事务行ID
     * @param usageCode 用途代码
     * @param requisitionLnsId 付款申请单行ID
     * @param drFlag 借方标识
     * @param crFlag 贷方标识
     * @return java.util.List<com.hand.hec.acp.dto.AcpRequisitionAccount>
     * @version 1.0
     **/
    AcpRequisitionAccount queryAccount(Long transactionLineId, String usageCode, Long requisitionLnsId, String drFlag,
                    String crFlag);

    /**
     * 借款申请单审核
     * @param request
     * @param list
     * @return
     * @author zhongyu 2019-5-9
     * @throws AcpRequisitionException
     */
    List<AcpRequisitionHd> auditAcpReq(IRequest request, List<AcpRequisitionHd> list)
            throws AcpRequisitionException;


    /**
     * 根据付款申请单行ID删除凭证
     *
     * @param requisitionLnsId 付款申请单行ID
     * @author guiyuting 2019-05-08 17:13
     * @return
     */
    void deleteByAcpLineId(Long requisitionLnsId);

    boolean createAcpRequisitionAccount(IRequest request, Long accEntityId, Date journalDate,
                    List<AcpRequisitionAccountTemp> accountTempList, List<AcpReqCurrencyTemp> currencyTempList);


    /**
     * 借款申请单拒绝
     * @param request
     * @param list
     * @return
     * @author zhognyu 2019-5-9
     * @throws AcpRequisitionException
     */
    List<AcpRequisitionHd> rejectAcpReq(IRequest request, List<AcpRequisitionHd> list)
            throws AcpRequisitionException;

}
