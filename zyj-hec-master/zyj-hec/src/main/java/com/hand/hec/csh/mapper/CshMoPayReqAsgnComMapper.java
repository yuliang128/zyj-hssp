package com.hand.hec.csh.mapper;

import java.util.List;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.csh.dto.CshMoPayReqAsgnCom;

import org.apache.ibatis.annotations.Param;

public interface CshMoPayReqAsgnComMapper extends Mapper<CshMoPayReqAsgnCom>{
    /**
     * 根据借款申请单类型Id查询已分配公司
     *
     * @param record   借款申请单类型分配公司实体类
     * @author dingwei.ma@hand-china.com 2019-02-20 13:41
     * @return List<CshMoPayReqAsgnCom>
     */
    List<CshMoPayReqAsgnCom> queryByTypeId(CshMoPayReqAsgnCom record);

    /**
     * 根据借款申请单类型Id查询未分配公司
     *
     * @param moPaymentReqTypeId   借款申请单类型Id
     * @param magOrgId   组织Id
     * @author dingwei.ma@hand-china.com 2019-02-20 13:43
     * @return List<CshMoPayReqAsgnCom>
     */
    List<CshMoPayReqAsgnCom> queryComByTypeId(@Param("moPaymentReqTypeId") Long moPaymentReqTypeId, @Param("magOrgId") Long magOrgId);
}