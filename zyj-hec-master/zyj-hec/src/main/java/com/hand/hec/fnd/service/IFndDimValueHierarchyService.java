package com.hand.hec.fnd.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.fnd.dto.FndDimValueHierarchy;

import java.util.List;

public interface IFndDimValueHierarchyService extends IBaseService<FndDimValueHierarchy>, ProxySelf<IFndDimValueHierarchyService>{

    /**
     * 根据维度ID 获取该维度结构树下所有的子维度
     *
     * @param dimensionId 维度ID
     * @author guiyuting 2019-05-27 14:37
     * @return
     */
    List<FndDimValueHierarchy> getTreeChildDims(IRequest request, Long dimensionId);

}