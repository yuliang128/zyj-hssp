package com.hand.hap.exp.service.impl;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.exp.dto.ExpEmployee;
import com.hand.hap.exp.mapper.ExpEmployeeMapper;
import com.hand.hap.exp.service.IExpEmployeeService;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 员工定义ServiceImpl
 * </p>
 *
 * @author xiuxian.wu 2019/01/25 14:48
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ExpEmployeeServiceImpl extends BaseServiceImpl<ExpEmployee> implements IExpEmployeeService {

    @Autowired
    private ExpEmployeeMapper employeeMapper;

    @Override
    protected boolean useSelectiveUpdate() {
        return false;
    }

    @Override
    public List<ExpEmployee> checkExpEmployee(String controlType, Long employeeId, String filtrateMethod,
            String controlEmployeeCodeFrom, String controlEmployeeCodeTo) {

        return employeeMapper.checkExpEmployee(controlType, employeeId, filtrateMethod, controlEmployeeCodeFrom,
                controlEmployeeCodeTo);
    }

    @Override
    public List<ExpEmployee> queryCurrentEmployee(IRequest request) {
        return employeeMapper.queryCurrentEmployee();
    }

    @Override
    public List<ExpEmployee> queryEmployeeByAuth(IRequest request, String docCategory) {
        return employeeMapper.queryEmployeeByAuth(docCategory);
    }

	@Override
	public List<ExpEmployee> queryEmployeeByCompAuth(IRequest request, String docCategory) {
		return employeeMapper.queryEmployeeByCompAuth(docCategory);
	}

	@Override
    public List<ExpEmployee> queryAllEmployeeByCondition(IRequest request, ExpEmployee condition, int page, int pageSize) {
        PageHelper.startPage(page,pageSize );
        return employeeMapper.queryAllEmployeeByCondition(condition);
    }

    @Override
    public List<ExpEmployee> getEmployeeNameByTaskId(Long taskId){
        return employeeMapper.getEmployeeNameByTaskId(taskId);
    }

    @Override
    public int checkExpEmployeeValidate(Long employeeId) {
        return employeeMapper.checkExpEmployeeValidate(employeeId);
    }

    @Override
    public List<ExpEmployee> getMostIdleEmpInTeam(Long workTeamId) {
        return employeeMapper.getMostIdleEmpInTeam(workTeamId);
    }
}
