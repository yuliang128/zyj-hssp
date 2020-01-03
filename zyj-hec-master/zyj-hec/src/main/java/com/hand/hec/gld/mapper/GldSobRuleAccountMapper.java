package com.hand.hec.gld.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.gld.dto.GldSobRuleAccount;

import java.util.List;

public interface GldSobRuleAccountMapper extends Mapper<GldSobRuleAccount>{

	/**
	 * 查询科目列表
	 * @param dto 查询条件
	 * @return 符合条件的科目列表
	 * @author  rui.shi@hand-china.com
	 */
	List<GldSobRuleAccount> queryGldSobRuleAccount(GldSobRuleAccount dto);


	/**
	 * 批量分配-查询未分配科目列表
	 * @param dto 查询条件
	 * @return 未分配的科目列表
	 * @author  rui.shi@hand-china.com
	 */
	List<GldSobRuleAccount> queryNotRefAccount(GldSobRuleAccount dto);
}