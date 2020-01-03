package com.hand.hec.csh.service;


import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.csh.dto.CshDocPayAccEntity;
import com.hand.hec.csh.dto.CshDocPayment;

import java.util.Date;
import java.util.List;

public interface ICshDocPayAccEntityService
                extends IBaseService<CshDocPayAccEntity>, ProxySelf<ICshDocPayAccEntityService> {

    /**
     * @Description 创建支付核算主体信息
     *
     * @Author Tagin
     * @Date 2019-03-14 20:20
     * @param iRequest 请求
     * @param docCategory 单据类别
     * @param docCompanyId 单据公司ID
     * @param docTypeId 单据类型ID
     * @param docId 单据头ID
     * @param docLineId 单据行ID Tips: 报销单计划付款行、借款单行、付款单行
     * @param defaultAccEntityId 默认核算主体
     * @param paymentMethodId 付款方式ID
     * @param payeeCategory 收款方类别
     * @param paymentFlag 支付标志
     * @param frozenFlag 冻结标志
     * @Return void
     * @Version 1.0
     **/
    void createPayAccEntity(IRequest iRequest, String docCategory, Long docCompanyId, Long docTypeId, Long docId,
                    Long docLineId, Long defaultAccEntityId, Long paymentMethodId, String payeeCategory,
                    String paymentFlag, String frozenFlag);

    /**
     * @Description 删除支付核算主体信息
     *
     * @Author Tagin
     * @Date 2019-03-14 19:01
     * @param iRequest 请求
     * @param docCategory 单据类别
     * @param docId 单据头ID
     * @param docLineId 单据行ID Tips: 报销单计划付款行、借款单行、付款单行
     * @Return void
     * @Version 1.0
     **/
    void deletePayAccEntity(IRequest iRequest, String docCategory, Long docId, Long docLineId);

    /**
     * @Description 单据支付-支付数据查询
     *
     * @Author Tagin
     * @Date 2019-03-05 19:35
     * @param request 请求
     * @param accEntityId 核算主体ID
     * @param docCategory 单据类别
     * @param docNumber 单据编号
     * @param payeeCategory 收款方对象
     * @param payeeId 收款方
     * @param requisitionDateFrom 申请日期从
     * @param requisitionDateTo 申请日期到
     * @param paymentMethodId 付款方式ID
     * @param paymentUsedeId 付款用途ID
     * @param schedulePaymentDateFrom 计划付款日期从
     * @param schedulePaymentDateTo 计划付款日期到
     * @param currencyCode 币种
     * @param page 页数
     * @param pageSize 每页大小
     * @Return java.util.List<com.hand.hec.csh.dto.CshDocPayAccEntity>
     * @Version 1.0
     **/
    List<CshDocPayAccEntity> queryPayment(IRequest request, Long accEntityId, String docCategory, String docNumber,
                    String payeeCategory, Long payeeId, Date requisitionDateFrom, Date requisitionDateTo,
                    Long paymentMethodId, Long paymentUsedeId, Date schedulePaymentDateFrom, Date schedulePaymentDateTo,
                    String currencyCode, int page, int pageSize);

    /**
     * @Description 单据支付
     *
     * @Author Tagin
     * @Date 2019-03-12 21:47
     * @param iRequest 请求
     * @param cshDocPayment 单据支付基础信息对象
     * @param cshDocPayAccEntities 需要支付数据集合
     * @Return void
     * @Version 1.0
     **/
    void executePayment(IRequest iRequest, CshDocPayment cshDocPayment, List<CshDocPayAccEntity> cshDocPayAccEntities);

    /**
     * 更新对应单据付款状态
     *
     * @param docId 单据头ID
     * @param docLineId 单据行ID【Tips：报销单计划付款行、借款申请单行、付款申请单行】
     * @param paymentStatus 支付状态
     * @param docCategory 单据类别
     * @param iRequest 请求
     * @author ngls.luhui 2019-03-11 19:30
     * @return
     */
    void updatePaymentStatus(Long docId, Long docLineId, String paymentStatus, String docCategory, IRequest iRequest);

    /**
     * 支付出纳退回
     *
     * @param dto
     * @author guiyuting 2019-06-17 10:26
     * @return 
     */
    void payBack(IRequest iRequest,List<CshDocPayAccEntity> dto);

}
