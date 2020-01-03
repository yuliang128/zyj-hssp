package com.hand.hec.exp.service;

import java.util.List;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.exp.dto.ExpMoEmpTypeAsgnCom;
import com.hand.hap.fnd.dto.FndCompany;

public interface IExpMoEmpTypeAsgnComService extends IBaseService<ExpMoEmpTypeAsgnCom>, ProxySelf<IExpMoEmpTypeAsgnComService> {

    /**
     * 根据员工类型查找该员工属于的公司，及公司的管理组织的一些信息
     *
     * @param employeeTypeId 员工类型ID
     * @return
     * @author ngls.luhui 2019-01-25 15:02
     */
     List<ExpMoEmpTypeAsgnCom> selectCompanyByEmpType(Long employeeTypeId);

    /**
     * 员工类型-管理公司 多对多分配
     *
     * @param company 包含有分配到的员工类型详细信息的公司集合
     * @return
     * @author ngls.luhui 2019-01-25 18:09
     */
     Boolean batchSubmit(IRequest requestCtx, List<FndCompany> company);

}