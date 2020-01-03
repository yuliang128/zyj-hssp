package com.hand.hec.gld.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.gld.dto.GldSobRuleAccount;

import java.util.List;

public interface IGldSobRuleAccountService extends IBaseService<GldSobRuleAccount>, ProxySelf<IGldSobRuleAccountService>{

	/**
	 * 查询科目列表
	 * @param requestContext requestContext
	 * @param dto 查询条件
	 * @return 符合条件的科目列表
	 * @author  rui.shi@hand-china.com
	 */
	List<GldSobRuleAccount> queryGldSobRuleAccount(IRequest requestContext, GldSobRuleAccount dto, int pageNum, int pageSize);

	List<GldSobRuleAccount> queryNotRefAccount(IRequest requestContext, GldSobRuleAccount dto, int pageNum, int pageSize);
}