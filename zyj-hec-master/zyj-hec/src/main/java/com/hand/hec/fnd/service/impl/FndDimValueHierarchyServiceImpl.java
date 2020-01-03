package com.hand.hec.fnd.service.impl;

import com.hand.hap.core.BaseConstants;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.fnd.mapper.FndDimValueHierarchyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hand.hec.fnd.dto.FndDimValueHierarchy;
import com.hand.hec.fnd.service.IFndDimValueHierarchyService;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class FndDimValueHierarchyServiceImpl extends BaseServiceImpl<FndDimValueHierarchy>
                implements IFndDimValueHierarchyService {
    @Autowired
    private FndDimValueHierarchyMapper hierarchyMapper;

    @Override
    public List<FndDimValueHierarchy> getTreeChildDims(IRequest request, Long dimensionId) {
        List<FndDimValueHierarchy> list = new ArrayList<>();
        FndDimValueHierarchy fndDimValueHierarchy =
                        FndDimValueHierarchy.builder().dimensionValueId(dimensionId).build();
        list.add(fndDimValueHierarchy);
        this.getChildren(request, dimensionId, list);
        return list;
    }

    private List<FndDimValueHierarchy> getChildren(IRequest request, Long parentDimValueId,
                    List<FndDimValueHierarchy> list) {
        FndDimValueHierarchy dimValueHierarchy =
                        FndDimValueHierarchy.builder().parentDimValueId(parentDimValueId).build();
        int num = hierarchyMapper.selectCount(dimValueHierarchy);
        if (num > 0) {
            List<FndDimValueHierarchy> subParents = hierarchyMapper.select(dimValueHierarchy);
            list.addAll(subParents);
            subParents.forEach(fndDimValueHierarchy -> {
                getChildren(request, fndDimValueHierarchy.getDimensionValueId(), list);
            });
        }
        return list;
    }
}
