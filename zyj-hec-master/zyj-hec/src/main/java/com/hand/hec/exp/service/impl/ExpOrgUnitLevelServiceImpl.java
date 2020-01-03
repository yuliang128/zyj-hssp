package com.hand.hec.exp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.exp.dto.ExpOrgUnitLevel;
import com.hand.hec.exp.mapper.ExpOrgUnitLevelMapper;
import com.hand.hec.exp.service.IExpOrgUnitLevelService;


/**
 * <p>
 * ExpOrgUnitLevelServiceImpl
 * </p>
 *
 * @author yang.duan 2019/01/10 11:18
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ExpOrgUnitLevelServiceImpl extends BaseServiceImpl<ExpOrgUnitLevel> implements IExpOrgUnitLevelService {
    @Autowired
    private ExpOrgUnitLevelMapper mapper;

    @Override
    public List<ExpOrgUnitLevel> checkExpOrgUnitLevel(String controlType, Long unitId, String filtrateMethod,
                                                      String controlUnitLevelCodeFrom, String controlUnitLevelCodeTo) {
        return mapper.checkExpOrgUnitLevel(controlType, unitId, filtrateMethod, controlUnitLevelCodeFrom,
                controlUnitLevelCodeTo);
    }
}
