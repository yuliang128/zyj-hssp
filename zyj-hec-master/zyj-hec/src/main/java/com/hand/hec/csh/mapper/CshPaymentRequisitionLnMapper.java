package com.hand.hec.csh.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.csh.dto.CshPaymentRequisitionLn;
import java.util.Map;

public interface CshPaymentRequisitionLnMapper extends Mapper<CshPaymentRequisitionLn>{
    /**
     * 获取现金流量项
     *
     * @param moCshTrxClassId
     * @param accEntityId
     * @param magOrgId
     * @author dingwei.ma@hand-china.com 2019-01-22 16:40
     * @return 
     */
    Long selectForFlowId(@Param("moCshTrxClassId") Long moCshTrxClassId, @Param("accEntityId") Long accEntityId, @Param("magOrgId") Long magOrgId);
    
    
    /**
     * 获取核算主体
     *
     * @param magOrgId
	 * @param documentCategory
	 * @param documentTypeId
	 * @param companyId
	 * @param paymentMethodId
	 * @param payeeCategory
     * @author dingwei.ma@hand-china.com 2019-01-22 16:41
     * @return 
     */
    Long selectForAccEntityId(@Param("magOrgId") Long magOrgId,@Param("companyId") Long companyId,@Param("documentCategory") String documentCategory,@Param("documentTypeId") Long documentTypeId,@Param("paymentMethodId") Long paymentMethodId,@Param("payeeCategory") String payeeCategory);

    /**
     * 获取现金流量项
     *
     * @param moCshTrxClassId
     * @param magOrgId
     * @author dingwei.ma@hand-china.com 2019-01-22 16:40
     * @return
     */
    Long selectForFlowId2(@Param("moCshTrxClassId") Long moCshTrxClassId, @Param("magOrgId") Long magOrgId);

	/**
	 * 借款申请单行查询
	 *
	 * @param paymentRequisitionHeaderId 借款申请单表头id
	 * @author LJK 2019-02-22 18:21
	 * @return List<Map>
	 */
	List<Map> selectByHeaderId(@Param("paymentRequisitionHeaderId") Long paymentRequisitionHeaderId);

    /**
     * 获取借款单创建凭证数据
     *@Author hui.zhao01@hand-china.com
     *@Date 2019/2/26 11:06
     *@param
     *@return
     *@Version 1.0
     **/
    List<CshPaymentRequisitionLn> getCshPaymentRequisitionLns(@Param("paymentRequisitionHeaderId")Long paymentRequisitionHeaderId);


    /**
     * <p>获取单据ID(付款反冲)</p>
     *
     * @param transactionHeaderId 现金事务头ID
     * @return Long
     * @author yang.duan 2019/5/23 15:48
    **/
    Long getDocId(@Param("transactionHeaderId") Long transactionHeaderId);

}