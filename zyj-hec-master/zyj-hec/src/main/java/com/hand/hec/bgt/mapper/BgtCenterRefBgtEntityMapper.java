package com.hand.hec.bgt.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.bgt.dto.BgtCenterRefBgtEntity;
/**
 * <p>
 * 预算中心关联预算实体Mapper
 * </p>
 * 
 * @author mouse 2019/01/07 16:47
 */
public interface BgtCenterRefBgtEntityMapper extends Mapper<BgtCenterRefBgtEntity>{

    /**
     * 預算中心-分配預算尸體-主查詢
     *
     * @param centerId 预算中心ID
     * @author ngls.luhui 2019-02-20 19:25
     * @return 与当前预算中心关联的预算实体
     */
    List<BgtCenterRefBgtEntity> queryMain(@Param("centerId") Long centerId);

    /**
     * 预算中心-分配预算实体-批量分配-查询可分配的预算实体
     *
     * @param centerId 预算中心id 不为空则不选出当前预算中心已分配过的
     * @param bgtOrgId 预算组织id
     * @author ngls.luhui 2019-02-21 11:16
     * @return 当前预算中心可分配的预算实体
     */
    List<BgtCenterRefBgtEntity> queryEntityCanAsgn(@Param("centerId") Long centerId,@Param("bgtOrgId") Long bgtOrgId);
}