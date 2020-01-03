package com.hand.hec.pur.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.pur.dto.PurSystemVenderRefAe;

/**
 * <p>
 * 供应商分配核算主体Mapper接口
 * </p>
 *
 * @author jialin.xing@hand-china.com 2019/02/20 15:45
 */
public interface PurSystemVenderRefAeMapper extends Mapper<PurSystemVenderRefAe> {

    /**
     * 供应商分配核算主体页面基础查询
     *
     * @param venderId 供应商ID
     * @return java.util.List<com.hand.hec.pur.dto.PurSystemVenderRefAe>
     * @author jialin.xing@hand-china.com 2019-02-20 16:33
     */
    List<PurSystemVenderRefAe> queryByVenderId(@Param("venderId") Long venderId);

    /**
     * 查询指定供应商未被分配的核算主体
     *
     * @param venderId 供应商ID
     * @return java.util.List<java.lang.Long>
     * @author jialin.xing@hand-china.com 2019-02-20 20:48
     */
    List<PurSystemVenderRefAe> queryUnallocatedAccEntity(@Param("venderId") Long venderId);
}