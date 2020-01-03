package com.hand.hec.bgt.service.impl;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.BaseConstants;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.bgt.mapper.BgtBudgetItemAsgnMoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hand.hec.bgt.dto.BgtBudgetItemAsgnMo;
import com.hand.hec.bgt.service.IBgtBudgetItemAsgnMoService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 预算项目分配管理组织ServiceImpl
 * </p>
 * 
 * @author mouse 2019/01/07 16:50
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class BgtBudgetItemAsgnMoServiceImpl extends BaseServiceImpl<BgtBudgetItemAsgnMo>
                implements IBgtBudgetItemAsgnMoService {
    @Autowired
    private BgtBudgetItemAsgnMoMapper bgtBudgetItemAsgnMoMapper;

    @Override
    public List<BgtBudgetItemAsgnMo> queryAll(IRequest request, BgtBudgetItemAsgnMo bgtBudgetItemAsgnMo, int page,
                    int pageSize) {
        PageHelper.startPage(page, pageSize);
        return bgtBudgetItemAsgnMoMapper.queryAll(bgtBudgetItemAsgnMo);
    }

    @Override
    public BgtBudgetItemAsgnMo submitBgtBudgetItemAsgnMo(IRequest request, BgtBudgetItemAsgnMo budgetItemAsgnMo) {
        BgtBudgetItemAsgnMo bgtBudgetItemAsgnMo =
                        BgtBudgetItemAsgnMo.builder().budgetItemId(budgetItemAsgnMo.getBudgetItemId())
                                        .magOrgId(budgetItemAsgnMo.getMagOrgId()).build();
        int num = bgtBudgetItemAsgnMoMapper.selectCount(bgtBudgetItemAsgnMo);
        if (num == 0) {
            bgtBudgetItemAsgnMo.setEnabledFlag(BaseConstants.YES);
            return self().insertSelective(request, bgtBudgetItemAsgnMo);
        }
        BgtBudgetItemAsgnMo updateBgtBudgetItemAsgnMo = self().select(request, bgtBudgetItemAsgnMo, 0, 0).get(0);
        bgtBudgetItemAsgnMoMapper.updateByBudgetItemId(updateBgtBudgetItemAsgnMo);
        return updateBgtBudgetItemAsgnMo;
    }
}
