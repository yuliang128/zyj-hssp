package com.hand.hec.bgt.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.bgt.dto.BgtBudgetItemHierarchy;
import net.sf.ehcache.search.parser.MValue;

import java.util.List;

/**
 * <p>
 * 预算项目层次Mapper
 * </p>
 * 
 * @author mouse 2019/01/07 16:46
 */
public interface BgtBudgetItemHierarchyMapper extends Mapper<BgtBudgetItemHierarchy>{
    
    /**
     *  根据parentItemId查询预算项目层次信息
     *
     * @param budgetItemHierarchy
     * @author guiyuting 2019-03-13 15:35
     * @return 符合条件的预算项目层次信息
     */
    List<BgtBudgetItemHierarchy> queryByParentItem(BgtBudgetItemHierarchy budgetItemHierarchy);

}