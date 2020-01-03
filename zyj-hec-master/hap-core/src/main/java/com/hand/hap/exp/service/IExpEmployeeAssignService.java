package com.hand.hap.exp.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.exp.dto.ExpEmployeeAssign;
import com.hand.hap.fnd.dto.FndCompany;
import com.hand.hap.system.service.IBaseService;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 员工分配Service
 * </p>
 *
 * @author xiuxian.wu 2019/01/25 14:48
 */
public interface IExpEmployeeAssignService
                extends IBaseService<ExpEmployeeAssign>, ProxySelf<IExpEmployeeAssignService> {

    /**
     * 查询员工具体信息
     *
     * @param condition 员工信息（员工ID）
     * @param request
     * @param pageNum
     * @param pageSize
     * @return 返回员工具体信息
     * @author xiuxian.wu 2019-02-25 14:43
     */
    List<ExpEmployeeAssign> queryExpEmployeeAssign(IRequest request, ExpEmployeeAssign condition, int pageNum,
                    int pageSize);

    /**
     * 通过员工Id查询公司Id
     *
     * @param employeeId 员工ID
     * @author jialin.xing@hand-china.com 2019-03-06 13:49
     * @return java.util.List<com.hand.hap.fnd.dto.FndCompany> 公司列表
     */
    List<FndCompany> getValidCompanyByEmployeeId(Long employeeId);

    /**
     * <p>
     * 根据公司和员工获取对应主岗信息()
     * <p/>
     * 
     * @param request
     * @param companyId 公司ID
     * @param employeeId 员工ID
     * @return 员工分配DTO
     * @author yang.duan 2019/3/12 13:57
     */
    ExpEmployeeAssign getEmployeeAssignInfo(IRequest request, Long companyId, Long employeeId);

}
