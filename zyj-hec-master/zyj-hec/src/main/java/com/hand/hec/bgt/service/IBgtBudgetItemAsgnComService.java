package com.hand.hec.bgt.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.bgt.dto.BgtBudgetItemAsgnCom;

import java.util.List;

/**
 * <p>
 * 预算项目分配公司Service
 * </p>
 * 
 * @author mouse 2019/01/07 16:53
 */
public interface IBgtBudgetItemAsgnComService
                extends IBaseService<BgtBudgetItemAsgnCom>, ProxySelf<IBgtBudgetItemAsgnComService> {

    /**
     * 根据assignMoId查询预算项目分配公司信息
     *
     * @param budgetItemAsgnCom
     * @author guiyuting 2019-03-12 14:42
     * @return 符合条件的分配公司信息
     */
    List<BgtBudgetItemAsgnCom> queryAll(IRequest request, BgtBudgetItemAsgnCom budgetItemAsgnCom, int page,
                    int pageSize);

    /**
     * 批量分配管理组织时更新
     *
     * @param budgetItemAsgnCom
     * @author guiyuting 2019-03-12 15:48
     * @return 更新完成的数据
     */
    BgtBudgetItemAsgnCom submitBgtBudgetItemAsgnCom(IRequest request,BgtBudgetItemAsgnCom budgetItemAsgnCom);

}
