package com.hand.hec.exp.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.exp.dto.ExpMoEmpTypeAsgnCom;

public interface ExpMoEmpTypeAsgnComMapper extends Mapper<ExpMoEmpTypeAsgnCom> {


    /**
     * 根据员工类型查找该员工属于的公司，及公司的管理组织的一些信息
     *
     * @param employeeTypeId 员工类型ID
     * @return
     * @author ngls.luhui 2019-01-25 15:02
     */
    public List<ExpMoEmpTypeAsgnCom> selectCompanyByEmpType(Long employeeTypeId);

    /**
     * 检查该管理公司和员工是否建立了分配关系
     *
     * @param employeeTypeId
     * @param companyId
     * @return 不为0，则建立，为0，则未建立
     * @author ngls.luhui 2019-01-28 10:40
     */
    public Long checkIfAsgn(@Param("employeeTypeId") Long employeeTypeId, @Param("companyId")Long companyId);

}