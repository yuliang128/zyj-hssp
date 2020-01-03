package com.hand.hec.csh.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.csh.dto.CshMoTrxClsRefFlowIt;

import java.util.List;

/**
 * <p>
 * 现金事物关联现金流量项service接口
 * </p>
 *
 * @author LJK 2019/02/19 12:10
 */
public interface ICshMoTrxClsRefFlowItService extends IBaseService<CshMoTrxClsRefFlowIt>, ProxySelf<ICshMoTrxClsRefFlowItService>{

	/**
	 * 查询现金事物关联现金流量项
	 *
	 * @param request 请求
	 * @param moCshTrxClassId 借款单类型Id
	 * @param pageNum 页码
	 * @param pageSize 页数
	 * @author LJK 2019-02-21 17:53
	 * @return
	 */
	List<CshMoTrxClsRefFlowIt> queryByTrxClassId(IRequest request, Long moCshTrxClassId, int pageNum, int pageSize);
}