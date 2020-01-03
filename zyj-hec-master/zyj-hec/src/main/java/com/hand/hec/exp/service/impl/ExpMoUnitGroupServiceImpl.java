package com.hand.hec.exp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.exp.dto.ExpMoUnitGroup;
import com.hand.hec.exp.mapper.ExpMoUnitGroupMapper;
import com.hand.hec.exp.service.IExpMoUnitGroupService;

/**
 * <p>
 * ExpMoUnitGroupServiceImpl
 * </p>
 *
 * @author yang.duan 2019/01/10 11:26
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ExpMoUnitGroupServiceImpl extends BaseServiceImpl<ExpMoUnitGroup> implements IExpMoUnitGroupService {

    @Autowired
    ExpMoUnitGroupMapper unitGroupMapper;

    @Override
    public List<ExpMoUnitGroup> checkExpOrgUnitGroup(String controlType, Long unitId, String filtrateMethod,
            String controlUnitGroupCodeFrom, String controlUnitGroupCodeTo) {
        return unitGroupMapper.checkExpOrgUnitGroup(controlType, unitId, filtrateMethod, controlUnitGroupCodeFrom,
                controlUnitGroupCodeTo);
    }
}
