package com.hand.hap.exp.service;

import java.util.List;

import java.util.List;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.exp.dto.ExpEmployeeLevel;
import com.hand.hap.system.service.IBaseService;

public interface IExpEmployeeLevelService extends IBaseService<ExpEmployeeLevel>, ProxySelf<IExpEmployeeLevelService> {

    /**
     * 预算检查的员工级别符合判断
     *
     * @param controlType 过滤类型，明细、汇总、全部
     * @param employeeId 当前占用行员工ID
     * @param filtrateMethod 控制规则取值方式
     * @param controlEmpLevelCodeFrom 控制规则员工职务代码从
     * @param controlEmpLevelCodeTo   控制规则员工职务代码到
     * @return 符合的员工级别
     * @author mouse 2019-01-10 15:50
     */
    List<ExpEmployeeLevel> checkExpEmpLevel(String controlType, Long employeeId, String filtrateMethod,
            String controlEmpLevelCodeFrom, String controlEmpLevelCodeTo);

    /**
     * @return 员工级别系列
     * @author Zhongyu 2019-2-15 15:04
     */
    List<ExpEmployeeLevel> queryExpEmpLevel(IRequest request, ExpEmployeeLevel employeeLevel, int pageNum,
            int pageSize);
}
