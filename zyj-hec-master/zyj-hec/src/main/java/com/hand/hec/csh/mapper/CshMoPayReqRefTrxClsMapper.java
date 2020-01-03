package com.hand.hec.csh.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.csh.dto.CshMoPayReqRefTrxCls;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CshMoPayReqRefTrxClsMapper extends Mapper<CshMoPayReqRefTrxCls>{
    List<CshMoPayReqRefTrxCls> queryByMagOrgId(CshMoPayReqRefTrxCls record);

    /**
     * 根据借款申请单类型获取配置的现金事物分类
     *
     * @param moPaymentReqTypeId 借款申请单类型ID
     * @author LJK 2019-04-02 18:42
     * @return List<CshMoPayReqRefTrxCls>
     */
	List<CshMoPayReqRefTrxCls> queryForLoan(@Param("moPaymentReqTypeId") Long moPaymentReqTypeId);
}