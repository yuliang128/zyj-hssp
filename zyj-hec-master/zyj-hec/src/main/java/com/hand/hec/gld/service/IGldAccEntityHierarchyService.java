package com.hand.hec.gld.service;

import java.util.List;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.gld.dto.GldAccEntityHierarchy;

public interface IGldAccEntityHierarchyService extends IBaseService<GldAccEntityHierarchy>, ProxySelf<IGldAccEntityHierarchyService> {

    /**
     * 查询出指定核算主体(汇总类型)的子核算主体
     *
     * @param parentEntityId 父核算主体的id
     * @param request
     * @Param page
     * @Param pageSize
     * @return
     * @author ngls.luhui 2019-01-22 16:24
     */
    public List<GldAccEntityHierarchy> selectChild(Long parentEntityId, IRequest request,Integer page,Integer pageSize);

    /**
     * 查找核算主体层次的树形结构
     *
     * @param null
     * @return 一个父子层级关系的核算主体层次集合
     * @author ngls.luhui 2019-01-23 14:50
     */
    public List<GldAccEntityHierarchy> selectTree();
}