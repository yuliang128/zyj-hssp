package com.hand.hap.exp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hand.hap.exp.dto.ExpEmployee;
import com.hand.hap.mybatis.common.Mapper;

/**
 * <p>
 * 员工定义Mapper
 * </p>
 *
 * @author xiuxian.wu 2019/01/25 14:48
 */
public interface ExpEmployeeMapper extends Mapper<ExpEmployee> {

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
    List<ExpEmployee> checkExpEmployee(@Param("controlType") String controlType, @Param("employeeId") Long employeeId,
            @Param("filtrateMethod") String filtrateMethod,
            @Param("controlEmployeeCodeFrom") String controlEmployeeCodeFrom,
            @Param("controlEmployeeCodeTo") String controlEmployeeCodeTo);


    /**
     * 查询系统当前员工
     *
     * @return List<ExpEmployee>
     * @author dingwei.ma@hand-china.com 2019-01-10 15:50
     */
    List<ExpEmployee> queryCurrentEmployee();

    /**
     * 当前员工和授权员工
     * @param docCategory       单据类型
     * @return List<ExpEmployee>
     * @author dingwei.ma@hand-china.com 2019-01-10 15:50
     */
    List<ExpEmployee> queryEmployeeByAuth(@Param("docCategory") String docCategory);

    /**
     * 查询当前公司下的授权员工
     *
     * @param docCategory       单据类型
     * @author LJK 2019-03-26 14:39
     * @return List<ExpEmployee>
     */
	List<ExpEmployee> queryEmployeeByCompAuth(@Param("docCategory") String docCategory);

    /**
     * 查询员工信息
     * @param condition
     * @return 返回员工信息
     * @author xiuxian.wu 2019-03-12 19:59
     */
    List<ExpEmployee> queryAllEmployeeByCondition(ExpEmployee condition);

    /**
     *根据工作台任务Id获取对应的员工
     *
     *@Author hui.zhao01@hand-china.com
     *@Date 2019/3/22 16:18
     *@param taskId 任务Id
     *@return List<ExpEmployee>
     *@Version 1.0
     **/
    List<ExpEmployee> getEmployeeNameByTaskId(@Param("taskId") Long taskId);

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

    /**
     * 获取员工
     *
     * @param parameterCode 参数代码（值）
     * @param parameterLowerLimit 参数代码始值
     * @param parameterUpperLimit 参数代码止值
     * @author YHL 2019-03-27 13:57
     * @return java.util.List<com.hand.hap.exp.dto.ExpEmployee> 员工
     */
    List<ExpEmployee> getEmployeeListForQuery(@Param("parameterCode") String parameterCode,
            @Param("parameterLowerLimit") String parameterLowerLimit,
            @Param("parameterUpperLimit") String parameterUpperLimit);

}
