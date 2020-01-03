package com.hand.hec.bgt.service.impl;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.bgt.mapper.BgtBudgetItemHierarchyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hand.hec.bgt.dto.BgtBudgetItemHierarchy;
import com.hand.hec.bgt.service.IBgtBudgetItemHierarchyService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 预算项目层次ServiceImpl
 * </p>
 * 
 * @author mouse 2019/01/07 16:50
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class BgtBudgetItemHierarchyServiceImpl extends BaseServiceImpl<BgtBudgetItemHierarchy>
                implements IBgtBudgetItemHierarchyService {
    @Autowired
    private BgtBudgetItemHierarchyMapper budgetItemHierarchyMapper;

    @Override
    public List<BgtBudgetItemHierarchy> queryByParentItem(IRequest request, BgtBudgetItemHierarchy budgetItemHierarchy,
                    int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        return budgetItemHierarchyMapper.queryByParentItem(budgetItemHierarchy);
    }
}
