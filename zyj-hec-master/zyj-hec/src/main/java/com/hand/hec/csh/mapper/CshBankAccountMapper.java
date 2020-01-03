package com.hand.hec.csh.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.csh.dto.CshBankAccount;

/**
 * <p>
 * 公司付款账户分配账户Mapper
 * </p>
 *
 * @author xiuxian.xu 2019-03-15 9:36
 */
public interface CshBankAccountMapper extends Mapper<CshBankAccount> {

    /**
     * 查询指定核算主体的银行账户的相关信息(汇率类型,汇率等)
     *
     * @param accEntityId
     * @param bankAccountName
     * @param bankAccountNum
     * @param currencyCode
     * @return java.util.List<com.hand.hec.csh.dto.CshBankAccount>
     * @author ngls.luhui 2019-03-06 16:53
     */
    List<CshBankAccount> queryBankAccounts(@Param("accEntityId") Long accEntityId,
                    @Param("bankAccountName") String bankAccountName, @Param("bankAccountNum") String bankAccountNum,
                    @Param("currencyCode") String currencyCode);

    /**
     * 获取银行账户对应的银行科目及成本中心
     *
     * @author Tagin
     * @date 2019-03-27 16:40
     * @param bankAccountId 银行科目ID
     * @param setOfBooksId 账套 ID
     * @return Map<String,Object>
     * @version 1.0
     **/
    Map<String, Object> queryAccountAndResp(@Param("bankAccountId") Long bankAccountId,
                    @Param("setOfBooksId") Long setOfBooksId);

    /**
     * 根据银行账户ID获取银行账号及银行信息
     *
     * @author Tagin
     * @date 2019-04-08 17:23
     * @param bankAccountId 银行账户ID
     * @return java.util.Map<java.lang.String,java.lang.Object>
     * @version 1.0
     **/
    Map<String, Object> queryAccountAndBank(@Param("bankAccountId") Long bankAccountId);

    /**
     * <p>获取核算主体下银行信息</p>
     *
     * @param accEntityId 核算主体ID
     * @return List<Map>
     * @author yang.duan 2019/5/30 10:00
    **/
    List<Map> queryBankAccountByAe(@Param("accEntityId") Long accEntityId);

}

