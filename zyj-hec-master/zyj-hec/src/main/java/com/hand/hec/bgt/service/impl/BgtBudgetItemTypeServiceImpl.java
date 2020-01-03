package com.hand.hec.bgt.service.impl;

import java.util.List;

import com.hand.hap.core.IRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.bgt.dto.BgtBudgetItemType;
import com.hand.hec.bgt.mapper.BgtBudgetItemTypeMapper;
import com.hand.hec.bgt.service.IBgtBudgetItemTypeService;

/**
 * <p>
 * 预算项目类型ServiceImpl
 * </p>
 *
 * @author mouse 2019/01/07 16:50
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class BgtBudgetItemTypeServiceImpl extends BaseServiceImpl<BgtBudgetItemType>
        implements IBgtBudgetItemTypeService {

    @Autowired
    BgtBudgetItemTypeMapper itemTypeMapper;

    @Override
    public List<BgtBudgetItemType> checkBgtItemType(String controlType, Long bgtOrgId, Long itemTypeId,
            String filtrateMethod, String controlItemTypeCodeFrom, String controlItemTypeCodeTo) {
        return itemTypeMapper
                .checkBgtItemType(controlType, bgtOrgId, itemTypeId, filtrateMethod, controlItemTypeCodeFrom,
                        controlItemTypeCodeTo);
    }

    @Override
    public List<BgtBudgetItemType> queryByBgtOrgId(IRequest request, BgtBudgetItemType bgtBudgetItemType) {
        return itemTypeMapper.queryByBgtOrgId(bgtBudgetItemType);
    }

    @Override
    protected boolean useSelectiveUpdate() {
        return false;
    }
}
