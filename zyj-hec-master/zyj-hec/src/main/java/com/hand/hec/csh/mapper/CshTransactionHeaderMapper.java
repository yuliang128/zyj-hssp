package com.hand.hec.csh.mapper;

import com.hand.hap.core.IRequest;
import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.csh.dto.CshTransactionHeader;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 现金事务Mapper
 * </p>
 *
 * @author Tagin 2019/01/22 10:28
 */
public interface CshTransactionHeaderMapper extends Mapper<CshTransactionHeader> {

    /**
     * 借款申请单查询交易信息
     *
     * @param headerId 借款申请单头Id
     * @return List<Map>
     * @author user 2019-02-27 13:51
     */

    List<Map> queryByPayReqHeaderId(@Param("headerId") Long headerId);

    /**
     * 借款申请单查询核销信息
     *
     * @param headerId 借款申请单头Id
     * @return List<Map>
     * @author user 2019-02-27 13:51
     */

    List<Map> queryWriteOffByPaYReqHeaderId(@Param("headerId") Long headerId);


    /**
     * 预付款核销页面信息
     *
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
    List<Map> queryPrePay(@Param("accEntityId") Long accEntityId, @Param("payeeCategory") String payeeCategory,
                    @Param("payeeId") Long payeeId, @Param("currencyCode") String currencyCode,
                    @Param("transactionNum") String transactionNum,
                    @Param("transactionDateFrom") String transactionDateFrom,
                    @Param("transactionDateTo") String transactionDateTo,
                    @Param("paymentMethodId") Long paymentMethodId, @Param("userName") String userName,
                    @Param("agentEmployeeId") Long agentEmployeeId, @Param("contactNumber") String contactNumber,
                    @Param("amountFrom") String amountFrom, @Param("amountTo") String amountTo,
                    @Param("requisitionNumber") String requisitionNumber, @Param("bankAccountId") Long bankAccountId);


    /**
     * 查询预付款核销过账页面的头信息
     *
     * @param transactionHeaderId 现金事务头ID
     * @return List<Map> 过账页面的头信息
     * @author ngls.luhui 2019-03-07 19:44
     */
    List<Map> queryPrePayWtfByHId(@Param("transactionHeaderId") Long transactionHeaderId);

    /**
     * 更新现金事务相关标识
     *
     * @author Tagin
     * @date 2019-03-29 10:57
     * @param postedFlag 过账标识
     * @param returnedFlag 还款标识
     * @param reversedFlag 反冲标识
     * @param reversedDate 反冲日期
     * @param transactionHeaderId 现金事务头ID
     * @param transactionType 现金事务类型
     * @param sourcePaymentHeaderId 来源现金事务头ID
     * @return void
     * @version 1.0
     **/
    void updateTrxHeaderFlag(@Param("postedFlag") String postedFlag, @Param("returnedFlag") String returnedFlag,
                    @Param("reversedFlag") String reversedFlag, @Param("reversedDate") Date reversedDate,
                    @Param("transactionHeaderId") Long transactionHeaderId,
                    @Param("transactionType") String transactionType,
                    @Param("sourcePaymentHeaderId") Long sourcePaymentHeaderId);

    /**
     * 获取预付款已退款金额
     *
     * @param sourceHeaderId
     * @author mouse 2019-05-05 15:12
     * @return java.math.BigDecimal
     */
    BigDecimal getPrepaymentReturnedAmount(@Param("sourceHeaderId") Long sourceHeaderId);


    /**
     * <p>
     * 付款反冲主页查询
     * </p>
     *
     * @param accEntityId 核算主体ID
     * @param payeeCategory 收款方类别
     * @param payeeId 收款方ID
     * @param currencyCode 币种
     * @param transactionNum 现金事务编号
     * @param transactionDateFrom 现金事务日期从
     * @param transactionDateTo 现金事务日期到
     * @param paymentMethodId 付款方式ID
     * @param paymentEmployeeName 支付员工名称
     * @param agentEmployeeName 代理员工名称
     * @param transactionAmountFrom 付款金额从
     * @param transactionAmountTo 付款金额到
     * @return List<Map>
     * @author yang.duan 2019/5/23 10:41
     **/
    List<Map> queryPaymentReverse(@Param("accEntityId") Long accEntityId, @Param("payeeCategory") String payeeCategory,
                    @Param("payeeId") Long payeeId, @Param("currencyCode") String currencyCode,
                    @Param("transactionNum") String transactionNum,
                    @Param("transactionDateFrom") String transactionDateFrom,
                    @Param("transactionDateTo") String transactionDateTo,
                    @Param("paymentMethodId") Long paymentMethodId,
                    @Param("paymentEmployeeName") String paymentEmployeeName,
                    @Param("agentEmployeeName") String agentEmployeeName,
                    @Param("transactionAmountFrom") String transactionAmountFrom,
                    @Param("transactionAmountTo") String transactionAmountTo);


    /**
     * <p>
     * 付款反冲明细页面付款信息查询
     * </p>
     * 
     * @param request
     * @param transactionHeaderId 现金事务头ID
     * @return List<Map>
     * @author yang.duan 2019/5/24 10:35
     **/
    List<Map> cshTransactionInfoQuery(@Param("request") IRequest request,
                    @Param("transactionHeaderId") Long transactionHeaderId);


    /**
     * <p>
     * 付款查询页面信息
     * </p>
     *
     * @param accEntityId 核算主体ID
     * @param agentEmployeeName 代理员工名称
     * @param bankAccountId 银行账户ID
     * @param contactNumber 合同编号
     * @param currencyCode 币种
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
     * @return List<Map>
     * @author yang.duan 2019/5/29 16:19
     **/
    List<Map> cshTransactionQuery(@Param("accEntityId") Long accEntityId,
                    @Param("transactionDateFrom") String transactionDateFrom,
                    @Param("transactionDateTo") String transactionDateTo, @Param("payeeCategory") String payeeCategory,
                    @Param("payeeId") Long payeeId, @Param("currencyCode") String currencyCode,
                    @Param("transactionNum") String transactionNum, @Param("bankAccountId") Long bankAccountId,
                    @Param("paymentMethodId") Long paymentMethodId,
                    @Param("paymentEmployeeName") String paymentEmployeeName,
                    @Param("agentEmployeeName") String agentEmployeeName, @Param("contactNumber") String contactNumber,
                    @Param("transactionType") String transactionType, @Param("transactionAmountFrom") String transactionAmountFrom,
                    @Param("transactionAmountTo") String transactionAmountTo,
                    @Param("sourceTransactionNum") String sourceTransactionNum);

	/**
	 * 预付款核销反冲查询
	 *
	 * @param payeeCategory 收款方类别(EMPLOYEE,VENDER,CUSTOMER)
	 * @param payeeId 收款方ID
	 * @param currencyCode 币种
	 * @param transactionNum 现金事务编号
	 * @param transactionDateFrom 付款日期从
	 * @param transactionDateTo 付款日期到
	 * @param requisitionNumber 单据编号
	 * @param contractNumber 合同编号
	 * @return java.util.List<java.util.Map>
	 * @author LJK 2019-06-04 16:16
	 */
	List<Map> queryPrePayForReverse(@Param("payeeCategory") String payeeCategory,
					@Param("payeeId") Long payeeId,
					@Param("currencyCode") String currencyCode,
					@Param("transactionNum") String transactionNum,
					@Param("transactionDateFrom") String transactionDateFrom,
					@Param("transactionDateTo") String transactionDateTo,
					@Param("requisitionNumber") String requisitionNumber,
					@Param("contractNumber") String contractNumber);

	/**
	 * <p>
	 * 借款单财务查询页面付款信息查询
	 * </p>
	 *
	 * @param transactionHeaderId 现金事务头ID
	 * @return List<Map>
	 * @author LJK 2019/6/13 10:35
	 **/
	List<Map> cshTransInfoQuery(@Param("transactionHeaderId") Long transactionHeaderId);
}
