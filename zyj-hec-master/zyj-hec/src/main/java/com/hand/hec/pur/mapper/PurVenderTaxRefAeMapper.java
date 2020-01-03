package com.hand.hec.pur.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.pur.dto.PurVenderTaxRefAe;

/**
 * <p>
 * 供应商税务信息分配核算主体Mapper接口
 * </p>
 *
 * @author jialin.xing@hand-china.com 2019/02/26 10:30
 */
public interface PurVenderTaxRefAeMapper extends Mapper<PurVenderTaxRefAe> {

    /**
     * 系统级供应商管理税务信息核算主体分配基础查询
     *
     * @param taxId 税务信息Id
     * @return java.util.List<com.hand.hec.pur.dto.PurVenderTaxRefAe>
     * @author jialin.xing@hand-china.com 2019-02-26 10:24
     */
    List<PurVenderTaxRefAe> queryByTaxId(@Param("taxId") Long taxId);

    /**
     * 根绝供应商Id和税务信息Id查询未分配的核算主体
     *
     * @param venderId 供应商Id
     * @param taxId    税务信息Id
     * @return java.util.List<com.hand.hec.pur.dto.PurVenderTaxRefAe>
     * @author jialin.xing@hand-china.com 2019-02-26 11:12
     */
    List<PurVenderTaxRefAe> queryAccEntityByVenderIdAndTaxId(@Param("venderId") Long venderId,
                                                             @Param("taxId") Long taxId);

}