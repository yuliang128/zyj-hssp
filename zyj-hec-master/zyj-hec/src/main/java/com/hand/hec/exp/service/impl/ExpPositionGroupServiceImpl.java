package com.hand.hec.exp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.exp.dto.ExpPositionGroup;
import com.hand.hec.exp.mapper.ExpPositionGroupMapper;
import com.hand.hec.exp.service.IExpPositionGroupService;

@Service
@Transactional(rollbackFor = Exception.class)
public class ExpPositionGroupServiceImpl extends BaseServiceImpl<ExpPositionGroup> implements IExpPositionGroupService {

    @Autowired
    ExpPositionGroupMapper positionGroupMapper;

    @Override
    public List<ExpPositionGroup> checkExpPositionGroup(String controlType, Long positionId, String filtrateMethod,
            String controlPositionGroupCodeFrom, String controlPositionGroupCodeTo) {
        return positionGroupMapper
                .checkExpPositionGroup(controlType, positionId, filtrateMethod, controlPositionGroupCodeFrom,
                        controlPositionGroupCodeTo);
    }
}
