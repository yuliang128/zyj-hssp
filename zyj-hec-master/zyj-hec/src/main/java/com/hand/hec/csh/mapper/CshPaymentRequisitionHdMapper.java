package com.hand.hec.csh.mapper;


import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.csh.dto.CshPaymentRequisitionHd;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 借款申请单头mapper
 * </p>
 *
 * @author LJK 2019/01/22 9:51
 */
public interface CshPaymentRequisitionHdMapper extends Mapper<CshPaymentRequisitionHd>{
    /**
     * update 借款行
     *
     * @param  record CshPaymentRequisitionHd
     * @author dingwei.ma@hand-china.com 2019-01-22 16:05

     */
    void updateByOptions(CshPaymentRequisitionHd record);
    
    /**
     * 获取时间戳
     *
     * @param date Date
     * @author dingwei.ma@hand-china.com 2019-01-22 16:09
     * @return Long
     */
    Long selectForTimestamp(@Param("date") Date date);

    /**
     * 获取行上金额之和
     *
     * @param paymentRequisitionHeaderId Long
     * @author dingwei.ma@hand-china.com 2019-01-22 16:09
     * @return Double
     */
    BigDecimal selectForLineSumAmount(@Param("paymentRequisitionHeaderId") Long paymentRequisitionHeaderId);

    /**
     * 关联申请行查询
     *
     * @param paymentRequisitionHeaderId Long
     * @author dingwei.ma@hand-china.com 2019-01-22 16:09
     * @return List<Long>
     */
    List<Long> selectForDisReqId(@Param("paymentRequisitionHeaderId") Long paymentRequisitionHeaderId);

    /**
     * 借款单关联申请单金额汇总
     *
     * @param paymentRequisitionHeaderId Long
     *        expRequisitionHeaderId  Long
     * @author dingwei.ma@hand-china.com 2019-01-22 16:09
     * @return BigDecimal
     */
    BigDecimal selectPayReqSumAmount(@Param("paymentRequisitionHeaderId") Long paymentRequisitionHeaderId,@Param("expRequisitionHeaderId") Long expRequisitionHeaderId);

    /**
     * 申请单已申请金额-已申请未付金额
     *
     * @param expRequisitionHeaderId  Long
     * @author dingwei.ma@hand-china.com 2019-01-22 16:09
     * @return BigDecimal
     */
    BigDecimal selectRemainAmount(@Param("expRequisitionHeaderId") Long expRequisitionHeaderId);

    /**
     * 关联申请单的借款单
     *
     * @param expRequisitionHeaderId  Long
     * @param payReqLineType String
     * @author dingwei.ma@hand-china.com 2019-01-22 16:09
     * @return List<CshPaymentRequisitionHd>
     */
    List<CshPaymentRequisitionHd> selectForPayReqHd(@Param("expRequisitionHeaderId") Long expRequisitionHeaderId,@Param("payReqLineType") String payReqLineType);

    /**
     * 关联申请单的借款单  去除单据状态判断
     *
     * @param expRequisitionHeaderId  Long
     * @param payReqLineType String
     * @author dingwei.ma@hand-china.com 2019-01-22 16:09
     * @return List<CshPaymentRequisitionHd>
     */
    List<CshPaymentRequisitionHd> selectForPayReqHd2(@Param("expRequisitionHeaderId") Long expRequisitionHeaderId,@Param("payReqLineType") String payReqLineType);

    /**
     * 申请单可支付金额之和
     *
     * @param expRequisitionHeaderId  Long
     * @param paymentFlag String
     * @author dingwei.ma@hand-china.com 2019-01-22 16:09
     * @return BigDecimal
     */
    BigDecimal selectForReqSumAmount(@Param("expRequisitionHeaderId") Long expRequisitionHeaderId,@Param("paymentFlag") String paymentFlag);

    /**
     * 借款单占用申请单金额之和
     *
     * @param expRequisitionHeaderId  Long
     * @param paymentReqLineType String
     * @author dingwei.ma@hand-china.com 2019-01-22 16:09
     * @return BigDecimal
     */
    BigDecimal selectForPayReqSumAmount(@Param("expRequisitionHeaderId") Long expRequisitionHeaderId,@Param("paymentReqLineType") String paymentReqLineType);

    /**
     * 查询借款单（按状态）
     *
     * @param record 借款申请单表头实体
     * @author LJK 2019-02-22 18:21
     * @return List<CshPaymentRequisitionHd>
     */
	List<CshPaymentRequisitionHd> queryPayReq(CshPaymentRequisitionHd record);

	/**
	 * 借款申请单明细查询
	 *
	 * @param paymentRequisitionHeaderId 借款申请单表头id
	 * @author LJK 2019-02-22 18:21
	 * @return List<Map>
	 */
	List<Map> selectDetailByHdId(@Param("paymentRequisitionHeaderId") Long paymentRequisitionHeaderId);

	/**
     * 借款申请单财务查询
     *
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
     * @author dingwei.ma@hand-china.com 2019-01-28 14:04
     * @return List<Map>
     */
    List<Map> queryForFinance(@Param("allCompanyFlag") String allCompanyFlag,
                              @Param("requisitionNumber") String requisitionNumber,
                              @Param("employeeId") Long employeeId,
                              @Param("description") String description,
                              @Param("requisitionDateFrom") Date requisitionDateFrom,
                              @Param("requisitionDateTo") Date requisitionDateTo,
                              @Param("amountFrom") BigDecimal amountFrom,
                              @Param("amountTo") BigDecimal amountTo,
                              @Param("payeeCategory") String payeeCategory,
                              @Param("payeeId") Long payeeId,
                              @Param("currencyCode") String currencyCode,
                              @Param("status") String status,
                              @Param("paymentMethodId") Long paymentMethodId,
                              @Param("cshPaymentRequisitionTypeId") Long cshPaymentRequisitionTypeId);

    /**
     * 借款单审核查询
     *
     * @param accEntityId  核算主体Id
     * @param employeeId   员工Id
     * @param requisitionNumber 借款单号
     * @param requisitionDateFrom 日期从
     * @param requisitionDateTo 日期到
     * @param currencyCode 币种
     * @param payeeCategory 收款对象
     * @param payeeId 收款方
     * @author dingwei.ma@hand-china.com 2019-01-22 16:09
     * @return List<Map>
     */
    List<Map> queryForAudit(@Param("accEntityId") Long accEntityId,@Param("employeeId") Long employeeId
                            ,@Param("requisitionNumber") String requisitionNumber,@Param("requisitionDateFrom") Date requisitionDateFrom
                            ,@Param("requisitionDateTo") Date requisitionDateTo,@Param("currencyCode") String currencyCode
                            ,@Param("payeeCategory") String payeeCategory,@Param("payeeId") Long payeeId);

    /**
     * 我的借款申请查询
     *
     * //@param paymentReqTypeId  单据类型
     * //@param requisitionNumber   单据编号
     * //@param amountFrom 金额从
     * //@param amountTo 金额到
     * //@param employeeId 员工
     * //@param createdDateFrom 日期从
     * //@param createdDateTo 日期到
     * //@param paymentCurrencyCode 币种
     * //@param payeeCategory 收款对象
     * //@param payeeId 收款方
     * //@param description 描述
     * //@param cshPayReqDateScope xxx
	 * @param dto
     * @author dingwei.ma@hand-china.com 2019-01-22 16:09
     * @return List<Map>
     */
    List<Map> queryPayRequisitionMain(CshPaymentRequisitionHd dto);
    //@Param("paymentReqTypeId") Long paymentReqTypeId,@Param("requisitionNumber") String requisitionNumber,@Param("amountFrom") BigDecimal amountFrom
	//                                     ,@Param("amountTo") BigDecimal amountTo,@Param("employeeId") Long employeeId,@Param("createdDateFrom") Date createdDateFrom
	//                                     ,@Param("createdDateTo") Date createdDateTo,@Param("paymentCurrencyCode") String paymentCurrencyCode,@Param("payeeCategory") String payeeCategory
	//                                     ,@Param("payeeId") Long payeeId,@Param("description") String description,@Param("cshPayReqDateScope") String cshPayReqDateScope


    /**
     * 费用申请财务关闭单查询   关闭、审批时调用(dto中需状态参数)
     *
     * @param cshPaymentRequisitionHd  借款申请单表头实体
     * @author weikun.wang2019-03-25 14:04
     * @return List<CshPaymentRequisitionHd>
     */
    List<CshPaymentRequisitionHd> queryForRequisitionReverse(@Param("cshPaymentRequisitionHd") CshPaymentRequisitionHd cshPaymentRequisitionHd);

    /**
     * 我的借款申请头查询
     *
     * @param paymentRequisitionHeaderId 借款申请单头id
	 * @param paymentReqTypeId 借款申请单头id
	 * @param employeeId 员工id
	 * @param accEntityId 核算主体id
     * @author LJK 2019-04-09 14:05
     * @return List<CshPaymentRequisitionHd>
     */
	List<CshPaymentRequisitionHd> queryPaymentRequisitionHeader(
			@Param("paymentRequisitionHeaderId") Long paymentRequisitionHeaderId,
			@Param("paymentReqTypeId") Long paymentReqTypeId, @Param("employeeId") Long employeeId,
			@Param("accEntityId") Long accEntityId);

    /**
     * 借款申请单关联报销单查询
     *
     * @param paymentRequisitionHeaderId 借款申请单表头id
     * @author MDW 2019-02-22 18:21
     * @return List<Map>
     */
    List<Map> selectPayReqRefReport(@Param("paymentRequisitionHeaderId") Long paymentRequisitionHeaderId);

    /**
     * 借款申请单关联还款单查询
     *
     * @param paymentRequisitionHeaderId 借款申请单表头id
     * @author MDW 2019-02-22 18:21
     * @return List<Map>
     */
    List<Map> selectPayReqRefRegister(@Param("paymentRequisitionHeaderId") Long paymentRequisitionHeaderId);
}