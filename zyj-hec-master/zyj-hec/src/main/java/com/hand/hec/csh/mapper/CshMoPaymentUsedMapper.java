package com.hand.hec.csh.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.csh.dto.CshMoPaymentUsed;

import java.util.List;

/**
 * <p>
 * 付款用途Mapper
 * </p>
 *
 * @author yang.cai@hand-chian.com 2019/02/26 19:48
 */
public interface CshMoPaymentUsedMapper extends Mapper<CshMoPaymentUsed> {
    /**
     * 根据条件查询付款用途
     * 
     * @param cshMoPaymentUsed
     * @return 符合条件的付款用途
     */
    List<CshMoPaymentUsed> queryAll(CshMoPaymentUsed cshMoPaymentUsed);

    /**
     * @Description 根据公司（管理组织）获取启用的付款用途
     *
     * @Author Tagin
     * @Date 2019-03-04 20:47
     *
     * @Return java.util.List<com.hand.hec.csh.dto.CshMoPaymentUsed>
     * @Version 1.0
     **/
    List<CshMoPaymentUsed> queryPaymentUsed();

}
