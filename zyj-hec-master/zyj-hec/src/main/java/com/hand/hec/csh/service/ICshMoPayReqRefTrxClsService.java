package com.hand.hec.csh.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.csh.dto.CshMoPayReqRefTrxCls;

import java.util.List;

public interface ICshMoPayReqRefTrxClsService extends IBaseService<CshMoPayReqRefTrxCls>, ProxySelf<ICshMoPayReqRefTrxClsService>{
    /**
     *查询借款单类型分配现金事务
     *
     * @param request 请求
     * @param condition 现金事务类
     * @param pageNum 页数
     * @param pageSize 页码
     * @author dingwei.ma@hand-china.com 2019-02-19 16:01
     * @return List<CshMoPayReqRefTrxCls>
     */
    List<CshMoPayReqRefTrxCls> queryByMagOrgId(IRequest request, CshMoPayReqRefTrxCls condition, int pageNum, int pageSize);

	/**
	 * 根据借款申请单类型获取配置的现金事物分类
	 *
	 * @param request 请求
	 * @param moPaymentReqTypeId 借款申请单类型ID
	 * @author LJK 2019-04-02 18:42
	 * @return List<CshMoPayReqRefTrxCls>
	 */
	List<CshMoPayReqRefTrxCls> queryForLoan(IRequest request, Long moPaymentReqTypeId);

}