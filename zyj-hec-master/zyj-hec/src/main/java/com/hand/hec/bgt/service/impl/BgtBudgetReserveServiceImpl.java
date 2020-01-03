package com.hand.hec.bgt.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.bgt.dto.BgtBudgetReserve;
import com.hand.hec.bgt.dto.BgtPeriod;
import com.hand.hec.bgt.mapper.BgtBudgetReserveMapper;
import com.hand.hec.bgt.service.IBgtBudgetReserveService;
import com.hand.hec.bgt.service.IBgtPeriodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 预算占用ServiceImpl
 * </p>
 * 
 * @author mouse 2019/01/07 16:50
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class BgtBudgetReserveServiceImpl extends BaseServiceImpl<BgtBudgetReserve> implements IBgtBudgetReserveService {


    @Autowired
    BgtBudgetReserveMapper budgetReserveMapper;

    @Autowired
    IBgtPeriodService bgtPeriodService;

    /**
     * <p>
     * 预算保留插入
     * <p/>
     *
     * @param request
     * @param budgetReserve
     * @return 返回新增后的预算保留DTO
     * @throws RuntimeException exception
     * @author yang.duan 2019/3/7 19:19
     */
    @Override
    public BgtBudgetReserve insertBgtReserve(IRequest request, BgtBudgetReserve budgetReserve) throws RuntimeException {
        // 获取预算年度
        BgtPeriod period = bgtPeriodService.getBgtPeriodYear(budgetReserve.getBgtOrgId(), budgetReserve.getPeriodName());
        // 预算保留表录入
        budgetReserve.setPeriodYear(period.getPeriodYear());
        budgetReserve.setInternalPeriodNum(period.getInternalPeriodNum());
        budgetReserve.setPeriodQuarter(period.getQuarterNum());
        budgetReserve.setCreationDate(new Date());
        budgetReserve.setCreatedBy(request.getUserId());
        budgetReserve.setLastUpdateDate(new Date());
        budgetReserve.setLastUpdatedBy(request.getUserId());
        budgetReserve = self().insertSelective(request, budgetReserve);
        // 计算其他币种的预算占用的扩展金额(未)



        // 触发预算保留调整事件(未)
        // exp_evt_pkg.fire_workflow_event(p_event_name => 'BGT_BUDGET_RESERVES_POST_MODIFY',
        // p_document_id => v_budget_reserve_line_id,
        // p_document_line_id => null,
        // p_source_module => 'BGT_BUDGET',
        // p_event_type => 'BGT_BUDGET_RESERVES_POST_MODIFY',
        // p_user_id => p_last_updated_by);
        return budgetReserve;
    }

    /**
     * <p>
     * 预算保留更新
     * <p/>
     *
     * @param request
     * @param budgetReserve
     * @return 返回更新后的预算保留DTO
     * @throws RuntimeException exception description
     * @author yang.duan 2019/3/12 10:06
     */
    public BgtBudgetReserve updateBgtReserve(IRequest request, BgtBudgetReserve budgetReserve) throws RuntimeException {
        // 查询预算保留信息
        BgtBudgetReserve dto = new BgtBudgetReserve();
        dto.setBusinessType(budgetReserve.getBusinessType());
        dto.setDocumentId(budgetReserve.getDocumentId());
        dto.setDocumentLineId(budgetReserve.getDocumentLineId());
        BgtBudgetReserve oldValue = budgetReserveMapper.select(dto).get(0);
        // 获取预算年度
        BgtPeriod period = bgtPeriodService.getBgtPeriodYear(budgetReserve.getBgtOrgId(), budgetReserve.getPeriodName());
        // 预算保留表录入
        budgetReserve.setBudgetReserveLineId(oldValue.getBudgetReserveLineId());
        budgetReserve.setPeriodYear(period.getPeriodYear());
        budgetReserve.setInternalPeriodNum(period.getInternalPeriodNum());
        budgetReserve.setPeriodQuarter(period.getQuarterNum());
        budgetReserve.setLastUpdateDate(new Date());
        budgetReserve.setLastUpdatedBy(request.getUserId());
        budgetReserve = self().updateByPrimaryKeySelective(request, budgetReserve);
        // 计算其他币种的预算占用的扩展金额(未)



        // 触发预算保留调整事件(未)
        // exp_evt_pkg.fire_workflow_event(p_event_name => 'BGT_BUDGET_RESERVES_POST_MODIFY',
        // p_document_id => v_budget_reserve_line_id,
        // p_document_line_id => null,
        // p_source_module => 'BGT_BUDGET',
        // p_event_type => 'BGT_BUDGET_RESERVES_POST_MODIFY',
        // p_user_id => p_last_updated_by);
        return budgetReserve;
    }

    @Override
    public List<BgtBudgetReserve> selectTaxAmountReserveInfo(Long expReportHeaderId, Long expReportLineId, Long expReportDistId){
        return budgetReserveMapper.selectTaxAmountReserveInfo(expReportHeaderId,expReportLineId,expReportDistId);
    }

    @Override
    public BgtBudgetReserve getBudgetReserveSum(String documentType, Long documentLineId) {
        return budgetReserveMapper.getBudgetReserveSum(documentType,documentLineId);
    }
}
