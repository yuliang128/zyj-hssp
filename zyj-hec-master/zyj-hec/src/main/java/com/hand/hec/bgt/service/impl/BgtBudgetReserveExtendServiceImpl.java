package com.hand.hec.bgt.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.bgt.dto.BgtBudgetReserveExtendBak;
import com.hand.hec.bgt.service.IBgtBudgetReserveExtendBakService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hand.hec.bgt.dto.BgtBudgetReserveExtend;
import com.hand.hec.bgt.service.IBgtBudgetReserveExtendService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 预算占用币种扩展ServiceImpl
 * </p>
 * 
 * @author mouse 2019/01/07 16:50
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class BgtBudgetReserveExtendServiceImpl extends BaseServiceImpl<BgtBudgetReserveExtend>
                implements IBgtBudgetReserveExtendService {

    @Autowired
    private IBgtBudgetReserveExtendBakService extendBakService;

    @Override
    public void archiveReserveExtend(IRequest request, Long budgetReserveLineId) {
        List<BgtBudgetReserveExtend> bgtBudgetReserveExtends = self().select(request,
                        BgtBudgetReserveExtend.builder().budgetReserveLineId(budgetReserveLineId).build(), 0, 0);
        bgtBudgetReserveExtends.forEach(bgtBudgetReserveExtend -> {
            BgtBudgetReserveExtendBak extendBak = new BgtBudgetReserveExtendBak();
            BeanUtils.copyProperties(bgtBudgetReserveExtend, extendBak);
            extendBakService.insertSelective(request, extendBak);
            self().deleteByPrimaryKey(bgtBudgetReserveExtend);
        });
    }
}
