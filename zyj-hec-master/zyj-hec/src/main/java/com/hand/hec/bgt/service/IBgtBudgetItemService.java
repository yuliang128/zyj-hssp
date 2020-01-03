package com.hand.hec.bgt.service;


import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.bgt.dto.BgtBudgetItem;

import java.util.List;


/**
 * <p>
 * 预算项目Service接口
 * </p>
 *
 * @author mouse 2019/01/07 14:43
 */
public interface IBgtBudgetItemService extends IBaseService<BgtBudgetItem>, ProxySelf<IBgtBudgetItemService> {

    /**
     * 预算检查的预算项目符合判断
     *
     * @param controlType 过滤类型，明细、汇总、全部
     * @param bgtOrgId 预算组织ID
     * @param filtrateMethod 控制规则取值方式
     * @param itemId 当前占用行预算实体ID
     * @param controlItemCodeFrom 控制规则预算项目代码从
     * @param controlItemCodeTo 控制规则预算项目代码到
     * @author mouse 2019-01-10 15:50
     * @return 符合的预算项目
     */
    List<BgtBudgetItem> checkBgtItem(String controlType, Long bgtOrgId, String filtrateMethod, Long itemId,
                    String controlItemCodeFrom, String controlItemCodeTo);

    /**
     * 根据预算组织ID，查询汇总标志为Y，启用的预算项目信息
     *
     * @param bgtBudgetItem 预算项目信息
     * @author guiyuting 2019-03-11 15:50
     * @return 符合条件的预算项目信息
     */
    List<BgtBudgetItem> queryHierarchyByBgtOrgId(IRequest request, BgtBudgetItem bgtBudgetItem, int page, int pageSize);

    /**
     * 预算项目批量分配管理组织及该管理组织下所有有效的管理公司
     *
     * @param bgtBudgetItemList
     * @author guiyuting 2019-03-12 15:37
     * @return
     */

    List<BgtBudgetItem> batchAssignMagOrg(IRequest request,List<BgtBudgetItem> bgtBudgetItemList);

    /**
     * <p>
     * 根据费用项目获取预算项目(未获取到则取申请项目关联的预算项目)
     * <p/>
     *
     * @param request
     * @param expenseItemId
     * @return 预算项目ID(找不到返回-1)
     * @author yang.duan 2019/3/6 17:04
     */
    Long getExpneseBgtItemId(IRequest request, Long expenseItemId);


    /**
     * 根据预算组织ID查询不为汇总，未被分配的预算项目
     *
     * @param bgtBudgetItem
     * @author guiyuting 2019-03-13 16:27
     * @return 符合条件的预算项目
     */
    List<BgtBudgetItem> queryNoSummaryByBgtOrg(IRequest request, BgtBudgetItem bgtBudgetItem);


    /**
     * 预算日记账类型定义分配预算项目项目查询
     *
     * @param bgtBudgetItem
     * @author guiyuting 2019-03-20 17:21
     * @return 符合条件的预算项目
     */
    List<BgtBudgetItem> bgtJournalBatchAssign(IRequest request, BgtBudgetItem bgtBudgetItem);

    /**
     * 根据预算项目ID 获取该预算项目结构树下所有的子预算项目
     *
     * @param bgtItemId 预算项目ID
     * @author guiyuting 2019-05-27 14:37
     * @return
     */
    List<BgtBudgetItem> getTreeChildItems(IRequest request, Long bgtItemId);

}
