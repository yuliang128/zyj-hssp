
package com.hand.hap.exp.mapper;


import com.hand.hap.exp.dto.ExpEmployeeAssign;
import com.hand.hap.fnd.dto.FndCompany;
import com.hand.hap.mybatis.common.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * <p>
 * 员工分配Mapper
 * </p>
 *
 * @author xiuxian.wu 2019/01/25 14:48
 */
public interface ExpEmployeeAssignMapper extends Mapper<ExpEmployeeAssign> {
    /**
     * 查询员工具体信息
     *
     * @param dto 员工信息（员工ID）
     * @return 返回员工具体信息
     * @author xiuxian.wu 2019-02-25 14:43
     */
    List<ExpEmployeeAssign> queryExpEmployeeAssign(ExpEmployeeAssign dto);

    /**
     * 通过员工Id查询公司Id
     *
     * @param employeeId 员工ID
     * @author jialin.xing@hand-china.com 2019-03-06 16:37
     * @return java.util.List<com.hand.hap.fnd.dto.FndCompany> 公司列表
     */
    List<FndCompany> getCompanyByEmployeeId(Long employeeId);
    /*
     * <p>根据公司和员工获取对应主岗信息()<p/>
     *
     * @param companyId 公司ID
     * @param employeeId 员工ID
     * @return 员工分配DTO
     * @author yang.duan 2019/3/12 13:57
     */
    ExpEmployeeAssign getEmployeeAssignInfo(@Param("companyId") Long companyId,@Param("employeeId") Long employeeId);

}