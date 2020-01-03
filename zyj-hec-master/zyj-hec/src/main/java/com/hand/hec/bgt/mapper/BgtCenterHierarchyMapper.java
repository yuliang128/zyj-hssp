package com.hand.hec.bgt.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.bgt.dto.BgtCenterHierarchy;
/**
 * <p>
 * 预算中心层次Mapper
 * </p>
 * 
 * @author mouse 2019/01/07 16:47
 */
public interface BgtCenterHierarchyMapper extends Mapper<BgtCenterHierarchy>{
    
    
    /**
     * 查询可分配的预算中心(明细)
     *
     * @param centerId 预算中心ID
     * @param bgtOrgId 预算组织ID
     * @author ngls.luhui 2019-02-21 17:23
     * @return 
     */
    List<BgtCenterHierarchy> queryDetailsCanAsgn(@Param("centerId") Long centerId,@Param("bgtOrgId") Long bgtOrgId);
}