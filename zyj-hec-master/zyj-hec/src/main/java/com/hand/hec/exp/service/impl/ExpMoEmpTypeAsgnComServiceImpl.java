package com.hand.hec.exp.service.impl;

import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.IBaseService;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.exp.dto.ExpMoEmpTypeAsgnCom;
import com.hand.hap.exp.dto.ExpMoEmployeeType;
import com.hand.hec.exp.mapper.ExpMoEmpTypeAsgnComMapper;
import com.hand.hec.exp.service.IExpMoEmpTypeAsgnComService;
import com.hand.hap.fnd.dto.FndCompany;

@Service
@Transactional(rollbackFor = Exception.class)
public class ExpMoEmpTypeAsgnComServiceImpl extends BaseServiceImpl<ExpMoEmpTypeAsgnCom> implements IExpMoEmpTypeAsgnComService {

    @Autowired
    private ExpMoEmpTypeAsgnComMapper mapper;

    @Override
    public List<ExpMoEmpTypeAsgnCom> selectCompanyByEmpType(Long employeeTypeId) {
        return mapper.selectCompanyByEmpType(employeeTypeId);
    }

    @Override
    public Boolean batchSubmit(IRequest requestCtx, List<FndCompany> company) {
        IBaseService<ExpMoEmpTypeAsgnCom> self = ((IBaseService<ExpMoEmpTypeAsgnCom>) AopContext.currentProxy());
        for (FndCompany fc : company) {
            List<ExpMoEmployeeType> emps = fc.getEmpTypeDetail();
            for (ExpMoEmployeeType emp : emps) {
                //针对选中的每一员工类型和每一公司一一检查是否建立过分配关系，然后做不同操作
                if (mapper.checkIfAsgn(emp.getEmployeeTypeId(), fc.getCompanyId()) == 0) {
                    //未分配过.进行绑定
                    ExpMoEmpTypeAsgnCom empfc = new ExpMoEmpTypeAsgnCom();
                    empfc.setCompanyId(fc.getCompanyId());
                    empfc.setEmployeeTypeId(emp.getEmployeeTypeId());
                    empfc.setEnabledFlag("Y");
                    self.insertSelective(requestCtx, empfc);
                }
            }
        }
        return true;
    }
}