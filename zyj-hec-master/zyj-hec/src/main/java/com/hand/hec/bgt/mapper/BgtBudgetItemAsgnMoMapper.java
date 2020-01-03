package com.hand.hec.bgt.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.bgt.dto.BgtBudgetItemAsgnMo;

import java.util.List;

/**
 * <p>
 * 预算项目分配管理组织Mapper
 * </p>
 *
 * @author mouse 2019/01/07 16:46
 */
public interface BgtBudgetItemAsgnMoMapper extends Mapper<BgtBudgetItemAsgnMo>{

    /**
     * 预算项目分配管理组织信息查询
     *
     * @param bgtBudgetItemAsgnMo
     * @author guiyuting 2019-03-11 19:30
     * @return 
     */
    List<BgtBudgetItemAsgnMo> queryAll(BgtBudgetItemAsgnMo bgtBudgetItemAsgnMo);

    /**
     * 批量分配时，根据预算项目ID和管理组织ID 更新
     *
     * @param bgtBudgetItemAsgnMo 预算项目分配管理组织信息
     * @author guiyuting 2019-03-12 16:03
     * @return
     */
    void updateByBudgetItemId(BgtBudgetItemAsgnMo bgtBudgetItemAsgnMo);

}