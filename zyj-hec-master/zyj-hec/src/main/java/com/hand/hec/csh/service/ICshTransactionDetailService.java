package com.hand.hec.csh.service;

import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.csh.dto.CshTransactionDetail;

import java.util.List;

public interface ICshTransactionDetailService extends IBaseService<CshTransactionDetail>, ProxySelf<ICshTransactionDetailService>{

	/**
	 * 查询字段名称 类型 注释
	 *@author bo.zhang 2019-03-06 15:42:26
	 * @return CshOfferFormatAsgnAe
	 */
	List<CshTransactionDetail> queryCshTransactionDetail();


}