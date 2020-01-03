package com.hand.hec.exp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.exp.dto.ExpOrgPosition;

/**
 * <p>
 * ExpOrgPositionMapper
 * </p>
 *
 * @author guiyuting 2019/02/26 18:50
 */
public interface ExpOrgPositionMapper extends Mapper<ExpOrgPosition> {

    /**
     * 预算检查的岗位符合判断
     *
     * @param controlType             过滤类型，明细、汇总、全部
     * @param positionId              当前占用行岗位ID
     * @param filtrateMethod          控制规则取值方式
     * @param controlPositionCodeFrom 控制规则岗位代码从
     * @param controlPositionCodeTo   控制规则岗位代码到
     * @return 符合的岗位
     * @author mouse 2019-01-10 15:50
     */
    List<ExpOrgPosition> checkExpPosition(@Param("controlType") String controlType, @Param("positionId") Long positionId, @Param("filtrateMethod") String filtrateMethod, @Param("controlPositionCodeFrom") String controlPositionCodeFrom, @Param("controlPositionCodeTo") String controlPositionCodeTo);

    /**
     * 查询岗位信息
     *
     * @param expOrgPosition 岗位信息
     * @return 符合条件的岗位
     * @author guiyu 2019-01-21 15:25
     */
    public List<ExpOrgPosition> queryPosition(ExpOrgPosition expOrgPosition);

    /**
     * 获取岗位
     *
     * @param parameterCode       参数代码（值）
     * @param parameterLowerLimit 参数代码始值
     * @param parameterUpperLimit 参数代码止值
     * @return java.util.List<com.hand.hec.exp.dto.ExpOrgPosition> 岗位
     * @author YHL 2019-03-27 18:48
     */
    List<ExpOrgPosition> getPositionListForQuery(@Param("parameterCode") String parameterCode, @Param("parameterLowerLimit") String parameterLowerLimit, @Param("parameterUpperLimit") String parameterUpperLimit);

    /**
     * 我的付款申请单维护页面 - 查询员工的职位和部门信息
     *
     * @param employeeId 员工ID
     * @return
     * @author guiyuting 2019-04-30 9:50
     */
    List<ExpOrgPosition> queryPositionAndUnit(@Param("employeeId") Long employeeId);


    /**
     * <p>根据公司ID和部门ID查询岗位</p>
     *
     * @param companyId    公司ID
     * @param unitId       部门ID
     * @param positionCode 岗位代码
     * @param positionName 岗位描述
     * @return List<ExpOrgPosition>
     * @author yang.duan 2019/5/16 11:26
     **/
    List<ExpOrgPosition> getPositionByUnitId(@Param("companyId") Long companyId, @Param("unitId") Long unitId, @Param("positionCode") String positionCode, @Param("positionName") String positionName);

}
