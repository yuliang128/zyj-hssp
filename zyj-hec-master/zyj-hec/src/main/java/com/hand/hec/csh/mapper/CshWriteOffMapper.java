package com.hand.hec.csh.mapper;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.csh.dto.CshWriteOff;

/**
 * <p>
 * 核销表Mapper
 * </p>
 *
 * @author Tagin 2019.01.22 09:50
 */

public interface CshWriteOffMapper extends Mapper<CshWriteOff> {

    /**
     * @Description 报销单核销借款-单据信息-查询
     * @Author Tagin
     * @Date 2019/2/18 10:30
     * @Param paymentScheduleLineId 报销单计划付款行ID
     * @Version 1.0
     **/
    List<CshWriteOff> docQuery(@Param("paymentScheduleLineId") Long paymentScheduleLineId);

    /**
     * @Description 报销单核销借款-未核销记录-查询
     * @Author Tagin
     * @Date 2019/1/29 10:30
     * @Param paymentScheduleLineId 报销单计划付款行ID
     * @Param companyId 系统当前公司ID
     * @Version 1.0
     **/
    List<CshWriteOff> unWriteQuery(@Param("paymentScheduleLineId") Long paymentScheduleLineId,
                    @Param("companyId") Long companyId, @Param("requisitionNumber") String requisitionNumber,
                    @Param("dateFrom") String dateFrom, @Param("dateTo") String dateTo);

    /**
     * @Description 报销单核销借款-已核销记录-查询
     * @Author Tagin
     * @Date 2019/2/14 17:49
     * @Param paymentScheduleLineId 报销单计划付款行ID
     * @Version 1.0
     **/
    List<CshWriteOff> writeQuery(@Param("paymentScheduleLineId") Long paymentScheduleLineId);

    /**
     * @Description 报销单核销借款-单据核销总金额-查询
     * @Author Tagin
     * @Date 2019/2/18 17:49
     * @Param documentHeaderId 单据头ID
     * @Param documentSource 来源单据类型
     * @Version 1.0
     **/
    BigDecimal totalDocAmount(@Param("documentHeaderId") Long documentHeaderId,
                    @Param("documentSource") String documentSource);

    /**
     * @Description 报销单核销借款-核销总金额-查询 [Tips：前两个参数一起使用，后两个参数一起使用分别从单据和现金事务维度查询核销金额]
     * @Author Tagin
     * @Date 2019/2/18 17:49
     * @Param documentHeaderId 单据头ID
     * @Param documentLineId 单据行ID
     * @Param documentSource 来源单据类型
     * @Param transactionType 现金事务类型
     * @Param transactionLineId 现金事物行ID
     * @Version 1.0
     **/
    BigDecimal totalWriteAmount(@Param("documentHeaderId") Long documentHeaderId,
                    @Param("documentLineId") Long documentLineId, @Param("documentSource") String documentSource,
                    @Param("transactionType") String transactionType,
                    @Param("transactionLineId") Long transactionLineId, @Param("writeOffType") String writeOffType);

    /**
     * @Description 报销单核销借款-最大核销日期-查询 [Tips：前两个参数一起使用，后一个参数使用分别从单据及现金事务维度查询最大核销日期]
     * @Author Tagin
     * @Date 2019/2/18 17:49
     * @Param documentHeaderId 单据头ID
     * @Param documentSource 来源单据类型
     * @Param transactionLineId 现金事务行ID
     * @Version 1.0
     **/
    Timestamp maxWriteDate(@Param("documentHeaderId") Long documentHeaderId,
                    @Param("documentSource") String documetnSource, @Param("transactionLineId") Long transactionLineId);

    /**
     * 预付款核销明细信息
     *
     * @param transactionHeaderId 现金事务头ID
     * @return List<CshWriteOff>
     * @author ngls.luhui 2019-03-08 13:46
     */
    List<CshWriteOff> queryPrePayDetail(@Param("transactionHeaderId") Long transactionHeaderId);

    /**
     * 根据报销单头Id获取核销数据
     *
     * @Author hui.zhao01@hand-china.com
     * @Date 2019/3/7 16:10
     * @param expReportHeaderId 报销单头Id
     * @return List<CshWriteOff>
     * @Version 1.0
     **/
    List<CshWriteOff> getCshWriteOffInfo(@Param("expReportHeaderId") Long expReportHeaderId);


    /**
     * 获取核销历史数据
     *
     * @param transactionHeaderId 现金事务头ID
     * @author ngls.luhui 2019-03-12 14:55
     * @return
     */
    List<Map> getWriteOffHistory(@Param("transactionHeaderId") Long transactionHeaderId);


    /**
     * 获取预付款核销的财务信息
     *
     * @param transactionHeaderId 现金事务头ID
     * @author ngls.luhui 2019-03-13 19:05
     * @return
     */
    List<Map> queryFinance(@Param("transactionHeaderId") Long transactionHeaderId);


    String submitCshWriteOffCheck(@Param("payeeCategory") String payeeCategory, @Param("payeeId") Long payeeId,
                    @Param("companyId") Long companyId, @Param("accEntityId") Long accEntityId);

    BigDecimal getCshUnWriteOffAmt(@Param("expReportHeaderId") Long expReportHeaderId);


    BigDecimal getRepPmtTotWriteOffAmt(@Param("expReportHeaderId") Long expReportHeaderId,
                    @Param("paymentScheduleLineId") Long paymentScheduleLineId);

    /**
     * 付款申请单提交校验 - 根据单据行ID查询已付款金额
     *
     * @param documentLineId
     * @author guiyuting 2019-05-06 16:22
     * @return
     */
    BigDecimal getPaidAmountByAcpReq(@Param("documentLineId") Long documentLineId);

    /**
     * 根据付款申请单行、现金事务获取核销数据
     *
     * @author Tagin
     * @date 2019-05-07 17:56
     * @param requisitionLnsId 付款申请单行 ID
     * @param transactionLineId 现金事务行 ID
     * @return com.hand.hec.csh.dto.CshWriteOff
     * @version 1.0
     **/
    List<CshWriteOff> getWriteOffByAcpRef(@Param("requisitionLnsId") Long requisitionLnsId,
                    @Param("transactionLineId") Long transactionLineId);

    /**
     * <p>
     * 获取单据ID(付款反冲)
     * </p>
     *
     * @param transactionHeaderId 现金事务头ID
     * @return CshWriteOff
     * @author yang.duan 2019/5/23 14:23
     **/
    List<CshWriteOff> getDocumentHeaderId(@Param("transactionHeaderId") Long transactionHeaderId);

    /**
     * <p>
     * 根据现金事务ID获取核销记录(付款反冲)
     * </p>
     *
     * @param transactionHeaderId 现金事务头ID
     * @return CshWriteOff
     * @author yang.duan 2019/5/23 15:31
     **/
    CshWriteOff getCshWriteOffByTransaction(@Param("transactionHeaderId") Long transactionHeaderId);


    /**
     * <p>
     * 核销信息查询(付款反冲)
     * </p>
     *
     * @param transactionHeaderId 现金事务头ID
     * @return List<Map>
     * @author yang.duan 2019/5/24 14:57
     **/
    List<Map> queryCshWriteOffHistory(@Param("transactionHeaderId") Long transactionHeaderId);

    /**
     * 报销单支付反冲核销记录查询
     *
     * @author Tagin
     * @date 2019-05-28 16:22
     * @param transactionLineId 现金事务行对象
     * @param returnFlag 还款标志
     * @return java.util.List<com.hand.hec.csh.dto.CshWriteOff>
     * @version 1.0
     **/
    List<CshWriteOff> queryRptWriteForReverse(@Param("transactionLineId") Long transactionLineId,
                    @Param("returnFlag") String returnFlag);

    /**
     * 借款申请单支付反冲核销记录查询
     *
     * @author Tagin
     * @date 2019-05-28 16:56
     * @param transactionLineId 现金事务行 ID
     * @param transactionHeaderId 现金事务头 ID
     * @param sourceHeaderId 还款事务头 ID
     * @param returnFlag 还款标志
     * @return java.util.List<com.hand.hec.csh.dto.CshWriteOff>
     * @version 1.0
     **/
    List<CshWriteOff> queryPayWriteForReverse(@Param("transactionLineId") Long transactionLineId,
                    @Param("transactionHeaderId") Long transactionHeaderId,
                    @Param("sourceHeaderId") Long sourceHeaderId, @Param("returnFlag") String returnFlag);

    /**
     * 付款申请单支付反冲核销记录查询
     *
     * @author Tagin
     * @date 2019-05-28 16:58
     * @param transactionLineId 现金事务行 ID
     * @param returnFlag 还款标志
     * @return java.util.List<com.hand.hec.csh.dto.CshWriteOff>
     * @version 1.0
     **/
    List<CshWriteOff> queryAcpWriteForReverse(@Param("transactionLineId") Long transactionLineId,
                    @Param("returnFlag") String returnFlag);

    /**
     * 根据类型获取核销记录
     *
     * @author Tagin
     * @date 2019-05-29 14:47
     * @param writeOffType 核销类型
     * @param transactionLineId 现金事务行 ID
     * @param documentSource 单据来源类型
     * @param sourceTrxLineId 预付款现金事务 ID
     * @return java.util.List<com.hand.hec.csh.dto.CshWriteOff>
     * @version 1.0
     **/
    List<CshWriteOff> getWriteByType(@Param("writeOffType") String writeOffType,
                    @Param("transactionLineId") Long transactionLineId, @Param("documentSource") String documentSource,
                    @Param("sourceTrxLineId") Long sourceTrxLineId);

	/**
	 * 预付款核销反冲核销记录查询
	 *
	 * @author LJK
	 * @date 2019-06-04 16:58
	 * @param transactionHeaderId 现金事务头 ID
	 * @return java.util.List<com.hand.hec.csh.dto.CshWriteOff>
	 * @version 1.0
	 **/
	List<CshWriteOff> queryPrePayWriteForReverse(@Param("transactionHeaderId") Long transactionHeaderId);

	/**
	 * @Description 报销单已核销金额
	 * @Author LJK
	 * @Date 2019/6/10 09:49
	 * @Param documentHeaderId 单据头ID
	 * @Param documentSource 来源单据类型
	 * @Version 1.0
	 **/
	BigDecimal totalOthersDocAmount(@Param("writeOffId") Long writeOffId);

}
