package com.hand.hec.exp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.exp.dto.ExpMoEmpGroup;
import com.hand.hec.exp.mapper.ExpMoEmpGroupMapper;
import com.hand.hec.exp.service.IExpMoEmpGroupService;

/**
 * <p>
 * 员工组定义ServiceImpl
 * </p>
 *
 * @author xiuxian.wu 2019/03/06 14:48
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ExpMoEmpGroupServiceImpl extends BaseServiceImpl<ExpMoEmpGroup> implements IExpMoEmpGroupService {

    @Autowired
    ExpMoEmpGroupMapper empGroupMapper;

    @Override
    public List<ExpMoEmpGroup> checkExpMoEmpGroup(String controlType, Long employeeId, String filtrateMethod,
                                                  String controlEmpGroupCodeFrom, String controlEmpGroupCodeTo) {
        return empGroupMapper.checkExpMoEmpGroup(controlType, employeeId, filtrateMethod, controlEmpGroupCodeFrom,
                controlEmpGroupCodeTo);
    }
}
