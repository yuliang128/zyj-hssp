package com.hand.hec.acp.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.acp.dto.AcpMoPayReqType;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 付款申请单类型mapper
 * </p>
 * 
 * @author guiyuting 2019/04/28 10:36
 */
public interface AcpMoPayReqTypeMapper extends Mapper<AcpMoPayReqType>{
    
    /**
     * 根据公司Id查询申请单类型信息
     *
     * @param companyId 公司ID
     * @author guiyuting 2019-04-28 10:38
     * @return
     */
    List<AcpMoPayReqType> queryByCompany(@Param("companyId") Long companyId);

    /**
     * 根据付款单类型 ID/付款申请单头 ID 获取自审核标志
     *
     * @author Tagin
     * @date 2019-04-30 15:06
     * @param moPayReqTypeId 付款申请单类型 ID
     * @param requisitionHdsId 付款申请单头 ID
     * @return java.lang.String
     * @version 1.0
     **/
    String getAuitFlag(@Param("moPayReqTypeId") Long moPayReqTypeId, @Param("requisitionHdsId") Long requisitionHdsId);

      /**
       * 我的付款申请-查询关联的付款申请单类型
       *
       * @param employeeId 员工ID
       * @param companyId 公司ID
       * @author guiyuting 2019-04-28 18:27
       * @return
       */
   List<AcpMoPayReqType> getAcpMoPayReqTypeList(@Param("employeeId") Long employeeId,@Param("companyId") Long companyId);
}