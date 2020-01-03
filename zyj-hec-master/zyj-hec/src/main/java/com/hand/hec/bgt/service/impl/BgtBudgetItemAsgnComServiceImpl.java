package com.hand.hec.bgt.service.impl;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.BaseConstants;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.bgt.mapper.BgtBudgetItemAsgnComMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hand.hec.bgt.dto.BgtBudgetItemAsgnCom;
import com.hand.hec.bgt.service.IBgtBudgetItemAsgnComService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 预算项目分配公司ServiceImpl
 * </p>
 *
 * @author mouse 2019/01/07 16:49
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class BgtBudgetItemAsgnComServiceImpl extends BaseServiceImpl<BgtBudgetItemAsgnCom>
                implements IBgtBudgetItemAsgnComService {
    @Autowired
    private BgtBudgetItemAsgnComMapper budgetItemAsgnComMapper;

    @Override
    public List<BgtBudgetItemAsgnCom> queryAll(IRequest request, BgtBudgetItemAsgnCom budgetItemAsgnCom, int page,
                    int pageSize) {
        PageHelper.startPage(page, pageSize);
        return budgetItemAsgnComMapper.queryAll(budgetItemAsgnCom);
    }

    @Override
    public BgtBudgetItemAsgnCom submitBgtBudgetItemAsgnCom(IRequest request, BgtBudgetItemAsgnCom budgetItemAsgnCom) {
        BgtBudgetItemAsgnCom bgtBudgetItemAsgnCom =
                        BgtBudgetItemAsgnCom.builder().assignMoId(budgetItemAsgnCom.getAssignMoId())
                                        .companyId(budgetItemAsgnCom.getCompanyId()).build();
        int num = budgetItemAsgnComMapper.selectCount(bgtBudgetItemAsgnCom);
        if(num == 0){
            bgtBudgetItemAsgnCom.setEnabledFlag(BaseConstants.YES);
            return self().insertSelective(request,bgtBudgetItemAsgnCom);
        }
        budgetItemAsgnComMapper.updateByAssignMoId(bgtBudgetItemAsgnCom);
        return budgetItemAsgnCom;
    }
}
