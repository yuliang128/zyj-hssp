package com.hand.hec.csh.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.fnd.dto.FndManagingOrganization;
import com.hand.hap.gld.dto.GldCurrency;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.csh.dto.CshMoPaymentReqType;

import java.util.List;

public interface ICshMoPaymentReqTypeService extends IBaseService<CshMoPaymentReqType>, ProxySelf<ICshMoPaymentReqTypeService>{
    /**
     * 查询管理组织
     *
     * @param request
     * @author dingwei.ma@hand-china.com 2019-02-18 14:22
     * @return List<FndManagingOrganization>
     */
    List<FndManagingOrganization> magOrgQuery(IRequest request);
    
    /**
     * 查询有效币种
     *
     * @param request
     * @author dingwei.ma@hand-china.com 2019-02-18 15:58
     * @return List<GldCurrency>
     */
    List<GldCurrency> currencyQuery(IRequest request);

    /**
     * 获取公司分配借款单类型
     *
     * @param request
     * @author dingwei.ma@hand-china.com 2019-02-18 15:58
     * @return List<CshMoPaymentReqType>
     */
    List<CshMoPaymentReqType> queryDftPayReqType(IRequest request);

	/**
	 * 查询借款单类型（我的借款申请）
	 *
	 * @param request
	 * @param employeeId 员工id
	 * @param moPaymentReqTypeCode 借款申请单类型代码
	 * @param description 借款申请单类型描述
	 * @author LJK 2019-03-27 15:55
	 * @return List<CshMoPaymentReqType>
	 */
	List<CshMoPaymentReqType> queryTypeForPayReq(IRequest request,Long employeeId,String moPaymentReqTypeCode,String description);

}