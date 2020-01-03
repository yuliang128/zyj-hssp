package com.hand.hap.exp.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.exp.dto.ExpEmployee;
import com.hand.hap.system.service.IBaseService;

import java.util.List;

/**
 * <p>
 * 员工定义Service
 * </p>
 *
 * @author xiuxian.wu 2019/01/25 14:48
 */
public interface IExpEmployeeService extends IBaseService<ExpEmployee>, ProxySelf<IExpEmployeeService> {

    /**
     * 预算检查的员工符合判断
     *
     * @param controlType             过滤类型，明细、汇总、全部
     * @param employeeId              当前占用行员工ID
     * @param filtrateMethod          控制规则取值方式
     * @param controlEmployeeCodeFrom 控制规则员工代码从
     * @param controlEmployeeCodeTo   控制规则员工代码到
     * @return 符合的员工
     * @author mouse 2019-01-10 15:50
     */
    List<ExpEmployee> checkExpEmployee(String controlType, Long employeeId, String filtrateMethod,
            String controlEmployeeCodeFrom, String controlEmployeeCodeTo);

    /**
     * 查询当前员工
     *
     * @param request             请求上下文
     * @return List<ExpEmployee>
     * @author dingwei.ma@hand-china.com 2019-01-10 15:50
     */
    List<ExpEmployee> queryCurrentEmployee(IRequest request);

    /**
     * 当前员工和授权员工
     *
     * @param request             请求上下文
     * @param docCategory         单据类型
     * @return List<ExpEmployee>
     * @author dingwei.ma@hand-china.com 2019-01-10 15:50
     */
    List<ExpEmployee> queryEmployeeByAuth(IRequest request,String docCategory);

	/**
	 * 查询当前公司下的授权员工
	 *
	 * @param request             请求上下文
	 * @param docCategory         单据类型
	 * @return List<ExpEmployee>
	 * @author LJK 2019-03-26 14:39
	 */
	List<ExpEmployee> queryEmployeeByCompAuth(IRequest request,String docCategory);

    /**
     * 查询员工信息
     * @param request
     * @param condition
     * @param page
     * @param pageSize
     * @return 返回员工信息
     * @author xiuxian.wu 2019-03-12 19:59
     */
    List<ExpEmployee> queryAllEmployeeByCondition(IRequest request,ExpEmployee condition,int page,int pageSize);

    /**
     *根据工作台任务Id获取对应的员工
     *
     *@Author hui.zhao01@hand-china.com
     *@Date 2019/3/22 16:18
     *@param taskId 任务Id
     *@return List<ExpEmployee>
     *@Version 1.0
     **/
    List<ExpEmployee> getEmployeeNameByTaskId(Long taskId);

    /**
     *校验员工是否失效
     *
     *@Author hui.zhao01@hand-china.com
     *@Date 2019/3/22 16:18
     *@param employeeId 员工Id
     *@return int
     *@Version 1.0
     **/
    int checkExpEmployeeValidate(Long employeeId);

    /**
     *获取工作组单据在手量最少的员工
     *
     *@Author hui.zhao01@hand-china.com
     *@Date 2019/3/22 16:18
     *@param workTeamId 工作组Id
     *@return int
     *@Version 1.0
     **/
    List<ExpEmployee> getMostIdleEmpInTeam(Long workTeamId);
}
