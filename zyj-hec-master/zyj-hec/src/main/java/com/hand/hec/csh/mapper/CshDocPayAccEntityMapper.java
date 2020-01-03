package com.hand.hec.csh.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.csh.dto.CshDocPayAccEntity;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface CshDocPayAccEntityMapper extends Mapper<CshDocPayAccEntity> {

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
     * @Return java.util.List<com.hand.hec.csh.dto.CshDocPayAccEntity>
     * @Version 1.0
     **/
    List<CshDocPayAccEntity> queryPayment(@Param("accEntityId") Long accEntityId,
                    @Param("docCategory") String docCategory, @Param("docNumber") String docNumber,
                    @Param("payeeCategory") String payeeCategory, @Param("payeeId") Long payeeId,
                    @Param("requisitionDateFrom") Date requisitionDateFrom,
                    @Param("requisitionDateTo") Date requisitionDateTo, @Param("paymentMethodId") Long paymentMethodId,
                    @Param("paymentUsedeId") Long paymentUsedeId,
                    @Param("schedulePaymentDateFrom") Date schedulePaymentDateFrom,
                    @Param("schedulePaymentDateTo") Date schedulePaymentDateTo,
                    @Param("currencyCode") String currencyCode);



    /**
     * 更新对应单据付款状态
     *
     * @param docId 单据头ID
     * @param docLineId 单据行ID【Tips：报销单计划付款行、借款申请单行、付款申请单行】
     * @param paymentStatus 支付状态
     * @param docCategory 单据类别
     * @author ngls.luhui 2019-03-11 19:30
     * @return
     */
    void updatePaymentStatus(@Param("docId") Long docId, @Param("docLineId") Long docLineId,
                    @Param("paymentStatus") String paymentStatus, @Param("docCategory") String docCategory);

    /**
     * @Description 删除支付核算主体信息
     *
     * @Author Tagin
     * @Date 2019-03-14 19:01
     * @param docCategory 单据类别
     * @param docId 单据头ID
     * @param docLineId 单据行ID Tips: 报销单计划付款行、借款单行、付款单行
     * @Return void
     * @Version 1.0
     **/
    void deletePayAccEntity(@Param("docCategory") String docCategory, @Param("docId") Long docId,
                    @Param("docLineId") Long docLineId);

    /**
     * 报销单支付出纳退回 - 检测该单据状态
     *
     * @param docId 单据头ID
     * @author guiyuting 2019-06-17 14:00
     * @return 
     */
    int countBackForExpReport(@Param("docId") Long docId);

    /**
     * 借款单支付出纳退回 - 检测该单据状态
     *
     * @param docId 单据头ID
     * @author guiyuting 2019-06-17 14:00
     * @return
     */
    int countBackForPaymentReq(@Param("docId") Long docId);

    /**
     * 付款申请单支付出纳退回 - 检测该单据状态
     *
     * @param docId 单据头ID
     * @author guiyuting 2019-06-17 14:00
     * @return
     */
    int countBackForAcpReq(@Param("docId") Long docId);

    /**
     * 从系统代码CSH_DOC_BACK获取值描述
     *
     * @param status
     * @author guiyuting 2019-06-17 15:09
     * @return 
     */
    String queryDocBackDesc(@Param("status") String status);

}
