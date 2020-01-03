package com.hand.hec.exp.service;

import java.util.List;
import java.util.Map;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.exp.dto.ExpOrgUnit;
import com.hand.hec.exp.exception.UnitAccOrBgtDfMultiException;

/**
 * <p>
 * IExpOrgUnitService
 * </p>
 *
 * @author yang.duan 2019/01/10 11:20
 */
public interface IExpOrgUnitService extends IBaseService<ExpOrgUnit>, ProxySelf<IExpOrgUnitService> {

    /**
     * 预算检查的部门符合判断
     *
     * @param controlType 过滤类型，明细、汇总、全部
     * @param unitId 当前占用行部门ID
     * @param filtrateMethod 控制规则取值方式
     * @param controlUnitCodeFrom 控制规则部门代码从
     * @param controlUnitCodeTo 控制规则部门代码到
     * @author mouse 2019-01-10 15:50
     * @return 符合的部门
     */
    List<ExpOrgUnit> checkExpOrgUnit(String controlType, Long unitId, String filtrateMethod, String controlUnitCodeFrom,
                    String controlUnitCodeTo);

    /**
     * 查找可分配的部门
     *
     * @param request
     * @param expOrgUnit
     * @author guiyu 2019-01-21 15:08
     * @return
     */
    List<ExpOrgUnit> queryAssignUnit(IRequest request, ExpOrgUnit expOrgUnit);

    /**
     * 根据公司查询部门信息
     *
     * @param expOrgUnit
     * @author guiyu 2019-01-22 19:09
     * @return 该公司下的部门
     */
    List<ExpOrgUnit> queryByCompany(IRequest request, ExpOrgUnit expOrgUnit, int page, int pageSize);

    /**
     * 提交部门信息
     *
     * @param request
     * @param expOrgUnits
     * @author guiyu 2019-01-22 19:09
     * @return 完成更新的部门信息
     */
    List<ExpOrgUnit> submitExpOrgUnit(IRequest request, List<ExpOrgUnit> expOrgUnits)
                    throws UnitAccOrBgtDfMultiException;


    /**
     * 获取某个员工在某个公司下的默认部门
     *
     * @param employeeId
     * @param companyId
     * @author mouse 2019-03-14 16:25
     * @return com.hand.hec.exp.dto.ExpOrgPosition
     */
    ExpOrgUnit getDefaultUnit(IRequest request, Long employeeId, Long companyId);

    /**
     * <p>
     * 检查父级岗位是否循环
     * <p/>
     *
     * @param request
     * @param unit 部门信息
     * @param unitId 部门ID
     * @return boolean
     */
    boolean checkParentLoop(IRequest request, ExpOrgUnit unit, Long unitId);


    /**
     * <p>
     *   单据行部门选择lov
     * </p>
     *
     * @param request
     * @param employeeId 员工ID
     * @param companyId  公司ID
     * @param unitCode 部门代码
     * @param unitName 部门名称
     * @return List<ExpOrgUnit>
     * @author yang.duan 2019/5/9 10:35
     **/
    List<Map> getUnitForEmployeeAssign(IRequest request,Long employeeId,Long companyId,String unitCode,String unitName);
}
