package com.hand.hec.bgt.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.bgt.dto.BgtBudgetItemAsgnCom;
import com.hand.hec.bgt.dto.BgtBudgetItemAsgnMo;

import java.util.List;

/**
 * <p>
 * 预算项目分配公司Mapper
 * </p>
 * 
 * @author mouse 2019/01/07 16:46
 */
public interface BgtBudgetItemAsgnComMapper extends Mapper<BgtBudgetItemAsgnCom>{

    /**
     * 根据assignMoId查询预算项目分配公司信息
     *
     * @param budgetItemAsgnCom
     * @author guiyuting 2019-03-12 14:42
     * @return 符合条件的分配公司信息
     */
    List<BgtBudgetItemAsgnCom> queryAll(BgtBudgetItemAsgnCom budgetItemAsgnCom);

    /**
     * 批量分配时，根据预算项目分配管理组织ID和公司ID 更新
     *
     * @param budgetItemAsgnCom 预算项目分配公司信息
     * @author guiyuting 2019-03-12 16:03
     * @return 
     */
    void updateByAssignMoId(BgtBudgetItemAsgnCom budgetItemAsgnCom);

}