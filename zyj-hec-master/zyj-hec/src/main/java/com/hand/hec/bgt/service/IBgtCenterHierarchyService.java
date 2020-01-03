package com.hand.hec.bgt.service;

import org.apache.ibatis.annotations.Param;

import java.util.List;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.bgt.dto.BgtCenterHierarchy;
/**
 * <p>
 * 预算中心层次Service
 * </p>
 * 
 * @author mouse 2019/01/07 16:55
 */
public interface IBgtCenterHierarchyService extends IBaseService<BgtCenterHierarchy>, ProxySelf<IBgtCenterHierarchyService>{

    /**
     * 查询可分配的预算中心(明细)
     *
     * @param centerId 预算中心ID
     * @param centerId 预算组织ID
     * @param request
     * @param page
     * @param pageSize
     * @author ngls.luhui 2019-02-21 17:23
     * @return
     */
    List<BgtCenterHierarchy> queryDetailsCanAsgn(Long centerId, Long bgtOrgId, IRequest request,Integer page,Integer pageSize);

}