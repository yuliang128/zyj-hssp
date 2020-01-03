package com.hand.hec.csh.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.csh.dto.CshPaymentBatchTpAsgnAe;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CshPaymentBatchTpAsgnAeMapper extends Mapper<CshPaymentBatchTpAsgnAe>{
    /**
     *获取可分配的核算主体
     *
     *@Author hui.zhao01@hand-china.com
     *@Date 2019/3/4 17:15
     *@param typeId 付款批类型Id
     *@param magOrgId 管理组织Id
     *@return List<CshPaymentBatchTpAsgnAe>
     *@Version 1.0
     **/
    List<CshPaymentBatchTpAsgnAe> queryCanAsgEntity(@Param("typeId")Long typeId,@Param("magOrgId")Long magOrgId);
}