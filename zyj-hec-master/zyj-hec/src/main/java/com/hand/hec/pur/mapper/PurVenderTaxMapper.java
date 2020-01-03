package com.hand.hec.pur.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.pur.dto.PurVenderTax;

/**
 * <p>
 * 供应商税务信息mapper接口
 * </p>
 *
 * @author jialin.xing@hand-china.com 2019/02/25 20:14
 */
public interface PurVenderTaxMapper extends Mapper<PurVenderTax> {

    /**
     * 供应商税务信息基础查询
     *
     * @param accEntityId 核算主体ID
     * @param venderId    供应商ID
     * @return java.util.List<com.hand.hec.pur.dto.PurVenderTax>
     * @author jialin.xing@hand-china.com 2019-03-02 21:56
     */
    List<PurVenderTax> queryByVenderIdAndAccEntityId(@Param("accEntityId") Long accEntityId,
                                                     @Param("venderId") Long venderId);

}