package com.hand.hec.bgt.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.bgt.dto.BgtBudgetReserve;
import java.util.List;
/**
 * <p>
 * 预算占用Service
 * </p>
 * 
 * @author mouse 2019/01/07 16:55
 */
public interface IBgtBudgetReserveService extends IBaseService<BgtBudgetReserve>, ProxySelf<IBgtBudgetReserveService>{
    /**
     * <p>预算保留插入<p/>
     *
     * @param request
     * @param budgetReserve
     * @return 返回新增后的预算保留DTO
     * @throws RuntimeException exception
     * @author yang.duan 2019/3/7 19:19
     */
    BgtBudgetReserve insertBgtReserve(IRequest request,BgtBudgetReserve budgetReserve) throws RuntimeException;

    /**
     * <p>预算保留更新<p/>
     *
     * @param request
     * @param budgetReserve
     * @return 返回更新后的预算保留DTO
     * @throws RuntimeException exception description
     * @author yang.duan 2019/3/12 10:06
     */
    BgtBudgetReserve updateBgtReserve(IRequest request,BgtBudgetReserve budgetReserve) throws RuntimeException;

    /**
     *获取拆分税金预算占用信息
     *
     *@Author hui.zhao01@hand-china.com
     *@Date 2019/3/14 10:40
     *@param expReportHeaderId 报销单头Id
     *@param expReportLineId 报销单行Id
     *@param expReportDistId 报销单分配行Id
     *@return
     *@Version 1.0
     **/
    List<BgtBudgetReserve>  selectTaxAmountReserveInfo(Long expReportHeaderId,Long expReportLineId,Long expReportDistId);

    /**
     *获取预算占用金额合计
     *
     *@Author hui.zhao01@hand-china.com
     *@Date 2019/3/29 16:48
     *@param documentType 单据类型
     *@param documentLineId 单据行Id
     *@return BgtBudgetReserve
     *@Version 1.0
     **/
    BgtBudgetReserve getBudgetReserveSum(String documentType, Long documentLineId);
}