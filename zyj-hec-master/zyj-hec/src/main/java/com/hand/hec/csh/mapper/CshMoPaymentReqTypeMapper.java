package com.hand.hec.csh.mapper;

import com.hand.hap.core.IRequest;
import com.hand.hap.fnd.dto.FndManagingOrganization;
import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.csh.dto.CshMoPaymentReqType;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CshMoPaymentReqTypeMapper extends Mapper<CshMoPaymentReqType> {
    List<FndManagingOrganization> magOrgQuery(IRequest request, @Param("magOrgId") Long magOrgId,
                    @Param("allOrganizationalFlag") String allOrganizationalFlag);

    /**
     * @Description 根据借款申请单头ID/借款申请单类型ID获取自审核标志
     *
     * @Author Tagin
     * @Date 2019-02-25 20:35
     * @param moPaymentReqTypeId 借款申请单类型ID [Tips：两个参数必须传入一个]
     * @param paymentRequisitionHeaderId 借款申请单头ID
     * @Return java.lang.String
     * @Version 1.0
     **/
    String getAuitFlag(@Param("moPaymentReqTypeId") Long moPaymentReqTypeId,
                    @Param("paymentRequisitionHeaderId") Long paymentRequisitionHeaderId);

    /**
     * @Description 获取公司分配借款单类型
     *
     * @Author dingwei.ma@hand-china.com
     * @Date 2019-02-25 20:35
     * @Return List<CshMoPaymentReqType>
     * @Version 1.0
     **/
    List<CshMoPaymentReqType> queryDftPayReqType();

    /**
     * 查询借款单类型（我的借款申请）
     *
     * @param employeeId 员工id
	 * @param moPaymentReqTypeCode 借款申请单类型代码
	 * @param description 借款申请单类型描述
     * @author LJK 2019-03-27 15:55
     * @return List<CshMoPaymentReqType>
     */
	List<CshMoPaymentReqType> queryTypeForPayReq(@Param("employeeId") Long employeeId,
			@Param("moPaymentReqTypeCode") String moPaymentReqTypeCode,
			@Param("description") String description);

}
