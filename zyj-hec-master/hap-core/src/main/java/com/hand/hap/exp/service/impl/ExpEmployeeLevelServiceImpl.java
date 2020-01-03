package com.hand.hap.exp.service.impl;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.hand.hap.core.IRequest;
import com.hand.hap.exp.dto.ExpEmployeeLevel;
import com.hand.hap.exp.mapper.ExpEmployeeLevelMapper;
import com.hand.hap.exp.service.IExpEmployeeLevelService;
import com.hand.hap.system.service.impl.BaseServiceImpl;

@Service
@Transactional(rollbackFor = Exception.class)
public class ExpEmployeeLevelServiceImpl extends BaseServiceImpl<ExpEmployeeLevel> implements IExpEmployeeLevelService {

    @Autowired
    ExpEmployeeLevelMapper employeeLevelMapper;

    @Override
    public List<ExpEmployeeLevel> checkExpEmpLevel(String controlType, Long employeeId, String filtrateMethod,
            String controlEmpLevelCodeFrom, String controlEmpLevelCodeTo) {
        return employeeLevelMapper.checkExpEmpLevel(controlType, employeeId, filtrateMethod, controlEmpLevelCodeFrom,

                controlEmpLevelCodeTo);
    }

    @Override
    public List<ExpEmployeeLevel> queryExpEmpLevel(IRequest request, ExpEmployeeLevel employeeLevel, int pageNum,
            int pageSize) {
        return employeeLevelMapper.queryExpEmpLevel(employeeLevel);
    }


}
