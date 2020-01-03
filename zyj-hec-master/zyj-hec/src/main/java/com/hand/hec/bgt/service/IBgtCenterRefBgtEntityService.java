package com.hand.hec.bgt.service;

import org.apache.ibatis.annotations.Param;

import java.util.List;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.bgt.dto.BgtCenter;
import com.hand.hec.bgt.dto.BgtCenterRefBgtEntity;
/**
 * <p>
 * 预算中心关联预算实体Service
 * </p>
 * 
 * @author mouse 2019/01/07 16:55
 */
public interface IBgtCenterRefBgtEntityService extends IBaseService<BgtCenterRefBgtEntity>, ProxySelf<IBgtCenterRefBgtEntityService>{

    /**
     * 預算中心-分配預算尸體-主查詢
     *
     * @param centerId 预算中心ID
     * @author ngls.luhui 2019-02-20 19:25
     * @return 与当前预算中心关联的预算实体
     */
    List<BgtCenterRefBgtEntity> queryMain(Long centerId,IRequest request,Integer page,Integer pageSize);

    /**
     * 预算中心-分配预算实体-批量分配-查询可分配的预算实体
     *
     * @param centerId 预算中心id 不为空则不选出当前预算中心已分配过的
     * @param bgtOrgId 预算组织id
     * @param request
     * @param page 分页
     * @param pageSize 分页
     * @author ngls.luhui 2019-02-21 11:16
     * @return 当前预算中心可分配的预算实体
     */
    List<BgtCenterRefBgtEntity> queryEntityCanAsgn(Long centerId,Long bgtOrgId,IRequest request,Integer page,Integer pageSize);

    /**
     *  预算中心定义-批量分配预算实体
     *
     * @param bgtCenterList 选定的要分配的预算中心集合，每个预算中心中包含了要被分配的预算实体信息
     * @Param request
     * @author ngls.luhui 2019-02-21 14:23
     * @return
     */
    Boolean batchSubmit(List<BgtCenter> bgtCenterList,IRequest request);
}