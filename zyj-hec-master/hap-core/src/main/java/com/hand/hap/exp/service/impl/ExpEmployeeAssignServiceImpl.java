package com.hand.hap.exp.service.impl;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.exp.dto.ExpEmployeeAssign;
import com.hand.hap.exp.mapper.ExpEmployeeAssignMapper;
import com.hand.hap.exp.service.IExpEmployeeAssignService;
import com.hand.hap.fnd.dto.FndCompany;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 员工分配ServiceImpl
 * </p>
 *
 * @author xiuxian.wu 2019/01/25 14:48
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ExpEmployeeAssignServiceImpl extends BaseServiceImpl<ExpEmployeeAssign>
                implements IExpEmployeeAssignService {
    @Autowired
    ExpEmployeeAssignMapper expEmployeeAssignMapper;

    @Override
    protected boolean useSelectiveUpdate() {
        return false;
    }

    @Override
    public List<ExpEmployeeAssign> queryExpEmployeeAssign(IRequest request, ExpEmployeeAssign condition, int pageNum,
                    int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return expEmployeeAssignMapper.queryExpEmployeeAssign(condition);
    }


    @Override
    public List<FndCompany> getValidCompanyByEmployeeId(Long employeeId) {
        List<FndCompany> companies =  expEmployeeAssignMapper.getCompanyByEmployeeId(employeeId);
        return companies.stream().filter(FndCompany::isActive).collect(Collectors.toList());
    }


    /**
     * <p>
     * 根据公司和员工获取对应主岗信息()
     * <p/>
     *@param request
     * @param companyId 公司ID
     * @param employeeId 员工ID
     * @return 员工分配DTO
     * @author yang.duan 2019/3/12 13:57
     */
    @Override
    public ExpEmployeeAssign getEmployeeAssignInfo(IRequest request,Long companyId, Long employeeId) {
        return expEmployeeAssignMapper.getEmployeeAssignInfo(companyId, employeeId);
    }
}
