package com.hand.hec.fnd.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.fnd.dto.PurVenderTypeRefAe;

/**
 * <p>
 * 供应商类型分配核算主体Mapper接口
 * </p>
 *
 * @author YHL 2019/01/29 12:47
 */
public interface PurVenderTypeRefAeMapper extends Mapper<PurVenderTypeRefAe> {

    /**
     * 查询供应商类型中分配的核算主体信息
     *
     * @param venderTypeId 供应商类型ID
     * @author YHL 2019-01-29 14:09
     * @return java.util.List<com.hand.hec.fnd.dto.PurVenderTypeRefAe> 供应商类型中分配的核算主体信息
     */
    List<PurVenderTypeRefAe> getVenderTypeRefAeByVenderTypeId(@Param("venderTypeId") Long venderTypeId);

    /**
     * 检查该核算主体和供应商类型是否建立了分配关系
     *
     * @param venderTypeId 供应商类型ID
     * @param accEntityId 核算主体ID
     * @author YHL 2019-02-01 16:24
     * @return java.lang.Long 不为0，则建立，为0，则未建立
     */
    public Long checkIfAsgn(@Param("venderTypeId") Long venderTypeId, @Param("accEntityId") Long accEntityId);
}