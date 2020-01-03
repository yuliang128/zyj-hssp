package com.hand.hec.exp.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.exp.dto.ExpEmployeeAssign;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.exp.dto.ExpOrgPosition;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * IExpOrgPositionService
 * </p>
 *
 * @author guiyuting 2019/02/26 18:43
 */
public interface IExpOrgPositionService extends IBaseService<ExpOrgPosition>, ProxySelf<IExpOrgPositionService> {

    /**
     * 预算检查的岗位符合判断
     *
     * @param controlType 过滤类型，明细、汇总、全部
     * @param positionId 当前占用行岗位ID
     * @param filtrateMethod 控制规则取值方式
     * @param controlPositionCodeFrom 控制规则岗位代码从
     * @param controlPositionCodeTo 控制规则岗位代码到
     * @author mouse 2019-01-10 15:50
     * @return 符合的岗位
     */
    List<ExpOrgPosition> checkExpPosition(String controlType, Long positionId, String filtrateMethod,
                    String controlPositionCodeFrom, String controlPositionCodeTo);

    /**
     * 查询岗位信息
     *
     * @param request
     * @param expOrgPosition 岗位信息
     * @param page
     * @param pageSize
     * @author guiyu 2019-01-21 15:25
     * @return 符合条件的岗位
     */
    List<ExpOrgPosition> queryPosition(IRequest request, ExpOrgPosition expOrgPosition, int page, int pageSize);

    /**
     * <p>
     * 检查父级岗位是否循环
     * <p/>
     *
     * @param request
     * @param position 岗位信息
     * @param positionId 岗位ID
     * @return boolean
     */
    boolean checkParentLoop(IRequest request, ExpOrgPosition position, Long positionId);

    /**
     * 获取某个员工在某个公司下的默认岗位
     *
     * @param employeeId
     * @param companyId
     * @author mouse 2019-03-14 16:25
     * @return com.hand.hec.exp.dto.ExpOrgPosition
     */
    ExpOrgPosition getDefaultPosition(IRequest request,Long employeeId, Long companyId);

    /**
     * 我的付款申请单维护页面 - 查询员工的职位和部门信息
     *
     * @param employeeId 员工ID
     * @author guiyuting 2019-04-30 9:50
     * @return
     */
    List<ExpOrgPosition> queryPositionAndUnit(IRequest request, Long employeeId);

    /**
     * <p>单据行岗位选择lov</p>
     *
     * @param request
     * @param employeeId  员工ID
     * @param companyId  公司ID
     * @param unitId 部门ID
     * @param positionCode 岗位代码
     * @param positionName 岗位描述
     * @return List<Map>
     * @author yang.duan 2019/5/16 10:03
    **/
    List<Map> getPositionForEmployeeAssign(IRequest request,Long employeeId,Long companyId,Long unitId,String positionCode,String positionName);
}
