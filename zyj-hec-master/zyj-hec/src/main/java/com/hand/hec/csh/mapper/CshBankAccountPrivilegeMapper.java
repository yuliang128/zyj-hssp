package com.hand.hec.csh.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.csh.dto.CshBankAccountPrivilege;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

public interface CshBankAccountPrivilegeMapper extends Mapper<CshBankAccountPrivilege>{

	/**
	 * 根据核算主体，银行代码，银行名称，查询出核算主体信息，账户信息，关联币种信息
	 *
	 * @param  @param accEntityId @param bankCode @param bankName
	 * @return 符合要求的银行，公司，账户信息，关联币种信息的集合
	 * @author bo.zhang05@hand-china.com 2019-02-2 15:40
	 */
	 List<CshBankAccountPrivilege> selectCshBankAccount(@Param("accEntityId") BigDecimal accEntityId, @Param("bankCode") String bankCode,
														@Param("bankName") String bankName);

	/**
	 * 根据bankAccountId查询出授权信息
	 *
	 * @param  @param bankAccountId
	 * @return 符合要求的授权信息集合
	 * @author bo.zhang05@hand-china.com 2019-02-22 16:43
	 */
	List<CshBankAccountPrivilege> selectCshBankAccountPrivilege(@Param("bankAccountId") BigDecimal bankAccountId);

}