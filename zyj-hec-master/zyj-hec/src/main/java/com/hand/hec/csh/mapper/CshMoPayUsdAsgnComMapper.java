package com.hand.hec.csh.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.csh.dto.CshMoPayUsdAsgnCom;
import org.apache.ibatis.annotations.Param;

import java.util.List;
/**
 * <p>
 * 付款用途分配公司Mapper
 * </p>
 *
 * @author yang.cai@hand-chian.com 2019/02/26 19:48
 */
public interface CshMoPayUsdAsgnComMapper extends Mapper<CshMoPayUsdAsgnCom>{
    /**
     * 根据条件查询付款用途分配公司
     * @param magOrgId
     * @param paymentUsedeId
     * @return 符合条件的付款用途分配公司
     */
    List<CshMoPayUsdAsgnCom> queryLov(@Param("magOrgId") Long magOrgId,@Param("paymentUsedeId") Long paymentUsedeId);

    List<CshMoPayUsdAsgnCom> query(CshMoPayUsdAsgnCom cshMoPayUsdAsgnCom);
}