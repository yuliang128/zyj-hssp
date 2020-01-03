package com.hand.hec.csh.service;

import java.util.List;
import java.util.Map;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.csh.dto.CshPaymentReqAccount;
import com.hand.hec.csh.dto.CshPaymentRequisitionHd;
import com.hand.hec.csh.exception.CshPaymentReqAccountException;
import com.hand.hec.fnd.dto.GldPeriod;

import java.util.Date;

/**
 * <p>
 * 借款申请单凭证表Service
 * </p>
 *
 * @author Tagin 2019/02/21 22:36
 */
public interface ICshPaymentReqAccountService
                extends IBaseService<CshPaymentReqAccount>, ProxySelf<ICshPaymentReqAccountService> {
    /**
     * 借款单审核凭证创建
     *
     * @param iRequest 请求上下文
     * @param paymentRequisitionHeaderId 借款单头Id
     * @param journalDate 凭证日期
     * @param periodName 期间
     * @return
     * @Author hui.zhao01@hand-china.com
     * @Date 2019/2/26 13:45
     * @Version 1.0
     **/
    void createCshPaymentReqAccount(IRequest iRequest, Long paymentRequisitionHeaderId, Date journalDate,
                    String periodName);

    /**
     * 借款单Id查询凭证
     *
     * @param request 上下文
     * @param paymentRequisitionHeaderId 头Id
     * @param pageNum 页码
     * @param pageSize 页数
     * @author dingwei.ma@hand-china.com 2019-02-26 13:59
     * @return List<Map>
     */

    List<Map> queryByHeaderId(IRequest request, Long paymentRequisitionHeaderId, int pageNum, int pageSize);

    /**
     * 根据公司Id查询核算主体信息
     *
     * @param request 上下文
     * @author dingwei.ma@hand-china.com 2019-02-26 13:59
     * @return List<Map>
     */

    List<Map> queryByCompanyId(IRequest request);

    /**
     * 查询当前时间
     *
     * @param request 上下文
     * @author dingwei.ma@hand-china.com 2019-02-26 13:59
     * @return List<Map>
     */

    List<Map> queryCurrentTime(IRequest request);

    /**
     * 创建借款申请单凭证
     *
     * @param request 请求上下文
     * @param list 借款申请单数列
     * @param journalDate 创建日期
     * @param periodName 期间
     * @author dingwei.ma@hand-china.com 2019-02-26 13:59
     * @return List<CshPaymentRequisitionHd>
     */

    List<CshPaymentRequisitionHd> createAccount(IRequest request, List<CshPaymentRequisitionHd> list,
                    String journalDate, String periodName) throws CshPaymentReqAccountException;

    /**
     * 借款申请单审核
     *
     * @param request 请求上下文
     * @param list 借款申请单数列
     * @author dingwei.ma@hand-china.com 2019-02-26 13:59
     * @return List<CshPaymentRequisitionHd>
     */

    List<CshPaymentRequisitionHd> auditPayReq(IRequest request, List<CshPaymentRequisitionHd> list)
                    throws CshPaymentReqAccountException;

    /**
     * 借款申请单复核
     *
     * @param request 请求上下文
     * @param list 借款申请单数列
     * @author dingwei.ma@hand-china.com 2019-02-26 13:59
     * @return List<CshPaymentRequisitionHd>
     */

    List<CshPaymentRequisitionHd> auditrecheckPayReq(IRequest request, List<CshPaymentRequisitionHd> list)
                    throws CshPaymentReqAccountException;

    /**
     * 借款申请单拒绝
     *
     * @param request 请求上下文
     * @param list 借款申请单数列
     * @author dingwei.ma@hand-china.com 2019-02-26 13:59
     * @return List<CshPaymentRequisitionHd>
     */

    List<CshPaymentRequisitionHd> rejectPayReq(IRequest request, List<CshPaymentRequisitionHd> list, String description)
                    throws CshPaymentReqAccountException;

    /**
     * 期间查询
     *
     * @param request 请求上下文
     * @param journalDate 借款申请单数列
     * @param status 状态
     * @author dingwei.ma@hand-china.com 2019-02-26 13:59
     * @return List<GldPeriod>
     */

    List<GldPeriod> queryPeriodName(IRequest request, Date journalDate, String status);

    /**
     * 期间查询
     *
     * @param request 请求上下文
     * @param list 借款申请单凭证list
     * @author dingwei.ma@hand-china.com 2019-02-26 13:59
     * @return List<GldPeriod>
     */

    List<CshPaymentReqAccount> updateAccount(IRequest request, List<CshPaymentReqAccount> list)
                    throws CshPaymentReqAccountException;


    /**
     * 获取借款审核凭证
     *
     * @author Tagin
     * @date 2019-04-01 14:51
     * @param transactionLineId 现金事务行ID
     * @param usageCode 用途代码
     * @param paymentRequisitionLineId 借款申请单行ID
     * @param drFlag 借方标识
     * @param crFlag 贷方标识
     * @return java.util.List<com.hand.hec.csh.dto.CshPaymentReqAccount>
     * @version 1.0
     **/
    CshPaymentReqAccount queryAccount(Long transactionLineId, String usageCode, Long paymentRequisitionLineId,
                    String drFlag, String crFlag);

}
