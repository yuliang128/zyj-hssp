package com.hand.hec.exp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.exp.dto.ExpEmployeeJob;
import com.hand.hec.exp.dto.ExpLevelSeries;
import com.hand.hec.exp.mapper.ExpEmployeeJobMapper;
import com.hand.hec.exp.mapper.ExpLevelSeriesMapper;
import com.hand.hec.exp.service.IExpEmployeeJobService;

import com.hand.hap.fnd.dto.FndManagingOrganization;
import com.hand.hap.fnd.mapper.FndManagingOrganizationMapper;
import com.hand.hap.sys.service.ISysParameterService;

@Service
@Transactional(rollbackFor = Exception.class)
public class ExpEmployeeJobServiceImpl extends BaseServiceImpl<ExpEmployeeJob> implements IExpEmployeeJobService {

    private static final String PARAMETER_CODE = "FND_ALL_ORGANIZATIONAL";

    @Autowired
    private ExpEmployeeJobMapper employeeJobMapper;
    @Autowired
    private ExpLevelSeriesMapper expLevelSeriesMapper;
    @Autowired
    private FndManagingOrganizationMapper fndManagingOrganizationMapper;
    @Autowired
    private ISysParameterService parameterService;

    @Override
    public List<ExpEmployeeJob> checkExpEmpJob(String controlType, Long employeeId, String filtrateMethod,
            String controlEmpJobCodeFrom, String controlEmpJobCodeTo) {
        return employeeJobMapper
                .checkExpEmpJob(controlType, employeeId, filtrateMethod, controlEmpJobCodeFrom, controlEmpJobCodeTo);
    }

    @Override
    public List<ExpEmployeeJob> queryForCb(IRequest request, ExpEmployeeJob employeeJob) {
        return employeeJobMapper.queryForCb(employeeJob);
    }

    @Override
    public List<ExpLevelSeries> queryForLevelService(IRequest request, ExpLevelSeries expLevelSeries) {
        return expLevelSeriesMapper.queryExpLevelSeries();
    }

    @Override
    public List<FndManagingOrganization> queryForManagingOrganization(IRequest request, FndManagingOrganization dto,
            Long roleId, Long userId, Long magOrgId) {
        String paramValue = this.parameterService
                .queryParamValueByCode(PARAMETER_CODE, userId, roleId, null, null, null, null, null);
        return fndManagingOrganizationMapper.queryFndManOrg(paramValue, magOrgId);
    }

    @Override
    public List<ExpEmployeeJob> queryEmpJobs(IRequest request, ExpEmployeeJob dto, int page, int pageSize) {
        return employeeJobMapper.queryEmpJobs(dto);
    }

}
