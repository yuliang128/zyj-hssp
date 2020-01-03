package com.hand.hec.csh.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.csh.dto.CshWriteOffAccount;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 核销凭证表Mapper
 * </p>
 *
 * @author Tagin 2019/01/22 10:24
 */
public interface CshWriteOffAccountMapper extends Mapper<CshWriteOffAccount> {
    /**
     * 获取报销单对应核销凭证信息
     *
     * @param paymentScheduleLineId 报销单计划付款行Id
     * @return List<CshWriteOffAccount>
     * @Author hui.zhao01@hand-china.com
     * @Date 2019/2/25 18:54
     * @Version 1.0
     **/
    List<CshWriteOffAccount> getWriteOffAccounts(@Param("paymentScheduleLineId") Long paymentScheduleLineId);

    /**
     * <p>
     * 付款反冲查询凭证信息
     * </p>
     *
     * @param transactionHeaderId 现金事务头ID
     * @return List<Map>
     * @author yang.duan 2019/5/24 11:37
     **/
    List<Map> cshPaymentFinanceInfo(@Param("transactionHeaderId") Long transactionHeaderId);

    /**
     * 查询核销凭证信息
     *
     * @author Tagin
     * @date 2019-05-29 16:58
     * @param writeOffId 核销 ID
     * @param usageCode 用途代码
     * @param drFlag 借方标记
     * @param crFlag 贷方标记
     * @return java.util.List<com.hand.hec.csh.dto.CshWriteOffAccount>
     * @version 1.0
     **/
    List<CshWriteOffAccount> queryAccount(@Param("writeOffId") Long writeOffId, @Param("usageCode") String usageCode,
                    @Param("drFlag") String drFlag, @Param("crFlag") String crFlag);

	/**
	 * <p>
	 * 预付款核销反冲查询凭证信息
	 * </p>
	 *
	 * @param writeOffId 凭证ID
	 * @return List<Map>
	 * @author LJK 2019/6/04 11:37
	 **/
	List<Map> cshPrepaymentFinanceInfo(@Param("writeOffId") Long writeOffId);

}
