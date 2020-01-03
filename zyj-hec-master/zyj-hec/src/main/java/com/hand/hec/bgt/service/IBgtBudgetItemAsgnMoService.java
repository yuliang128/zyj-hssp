package com.hand.hec.bgt.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.bgt.dto.BgtBudgetItemAsgnMo;

import java.util.List;

/**
 * <p>
 * 预算项目分配管理组织Service
 * </p>
 * 
 * @author mouse 2019/01/07 16:53
 */
public interface IBgtBudgetItemAsgnMoService
                extends IBaseService<BgtBudgetItemAsgnMo>, ProxySelf<IBgtBudgetItemAsgnMoService> {

    /**
     * 预算项目分配管理组织信息查询
     *
     * @param bgtBudgetItemAsgnMo
     * @author guiyuting 2019-03-11 19:30
     * @return
     */
    List<BgtBudgetItemAsgnMo> queryAll(IRequest request, BgtBudgetItemAsgnMo bgtBudgetItemAsgnMo, int page,
                    int pageSize);

    /**
     * 批量分配管理组织时更新
     *
     * @param budgetItemAsgnMo
     * @author guiyuting 2019-03-12 15:48
     * @return 更新完成的数据
     */
    BgtBudgetItemAsgnMo submitBgtBudgetItemAsgnMo(IRequest request,BgtBudgetItemAsgnMo budgetItemAsgnMo);

}
