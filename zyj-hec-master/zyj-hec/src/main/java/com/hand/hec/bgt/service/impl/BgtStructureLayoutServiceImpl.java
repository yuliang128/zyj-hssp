package com.hand.hec.bgt.service.impl;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.DTOStatus;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.bgt.exception.BgtStructureException;
import com.hand.hec.bgt.mapper.BgtStructureLayoutMapper;
import com.hand.hec.bgt.service.IBgtStructureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hand.hec.bgt.dto.BgtStructureLayout;
import com.hand.hec.bgt.service.IBgtStructureLayoutService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 预算表维度布局ServiceImpl
 * </p>
 * 
 * @author mouse 2019/01/07 16:53
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class BgtStructureLayoutServiceImpl extends BaseServiceImpl<BgtStructureLayout>
                implements IBgtStructureLayoutService {
    @Autowired
    private BgtStructureLayoutMapper layoutMapper;

    @Autowired
    private IBgtStructureService structureService;

    @Override
    public List<BgtStructureLayout> queryByStructureId(IRequest request, Long structureId, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        return layoutMapper.queryByStructureId(structureId);
    }

    @Override
    public List<BgtStructureLayout> batchUpdate(IRequest request, List<BgtStructureLayout> list) {
        for (BgtStructureLayout bgtStructureLayout : list) {
            switch (bgtStructureLayout.get__status()) {
                case DTOStatus.ADD:
                    this.insertBgtStructureLayout(request, bgtStructureLayout);
                    break;
                case DTOStatus.UPDATE:
                    this.updateBgtStructureLayout(request, bgtStructureLayout);
                    break;
                case DTOStatus.DELETE:
                    self().deleteByPrimaryKey(bgtStructureLayout);
                    break;
                default:
                    break;
            }
        }
        return list;
    }

    /**
     * 预算表维度布局信息插入
     *
     * @param bgtStructureLayout 预算表维度布局信息
     * @author guiyuting 2019-03-05 18:57
     * @return
     */
    private BgtStructureLayout insertBgtStructureLayout(IRequest request, BgtStructureLayout bgtStructureLayout) {
        if (structureService.budgetStructureInvokeCheck(bgtStructureLayout.getStructureId())
                        || this.budgetStructureInvokeCheck(bgtStructureLayout)) {
            throw new BgtStructureException(BgtStructureException.BUDGET_DIMENSION_DUPLICATE, null);
        }
        self().insertSelective(request, bgtStructureLayout);
        return bgtStructureLayout;
    }

    /**
     * 预算表维度布局信息更新
     *
     * @param bgtStructureLayout 预算表维度布局信息
     * @author guiyuting 2019-03-05 18:57
     * @return
     */
    private BgtStructureLayout updateBgtStructureLayout(IRequest request, BgtStructureLayout bgtStructureLayout) {

        BgtStructureLayout structureLayout = self().selectByPrimaryKey(request, BgtStructureLayout.builder()
                        .structureLayoutId(bgtStructureLayout.getStructureLayoutId()).build());
        if (structureService.budgetStructureInvokeCheck(bgtStructureLayout.getStructureId())) {
            if (structureLayout.getLayoutPosition() != bgtStructureLayout.getLayoutPosition()
                            || !structureLayout.getLayoutPriority().equals(bgtStructureLayout.getLayoutPriority())
                            || !structureLayout.getDimensionId().equals(bgtStructureLayout.getDimensionId())) {
                throw new BgtStructureException(BgtStructureException.BUDGET_DIMENSION_DUPLICATE, null);
            }
        }
        return self().updateByPrimaryKey(request, bgtStructureLayout);
    }

    /**
     * 检查预算表是否被预算日记账引用
     *
     * @param bgtStructureLayout 预算表维度布局信息
     * @author guiyuting 2019-03-05 15:48
     * @return true被引用，false未被引用
     */
    private boolean budgetStructureInvokeCheck(BgtStructureLayout bgtStructureLayout) {
        int num = layoutMapper.selectCount(BgtStructureLayout.builder().dimensionId(bgtStructureLayout.getDimensionId())
                        .structureId(bgtStructureLayout.getStructureId()).build());
        return num == 0 ? false : true;
    }
}
