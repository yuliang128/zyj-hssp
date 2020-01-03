package com.hand.hec.exp.service;

import java.util.List;

import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.exp.dto.ExpMoEmpGroup;

/**
 * <p>
 * 员工组定义Service
 * </p>
 *
 * @author xiuxian.wu 2019/03/06 14:48
 */
public interface IExpMoEmpGroupService extends IBaseService<ExpMoEmpGroup>, ProxySelf<IExpMoEmpGroupService> {

    /**
     * 预算检查的员工组符合判断
     *
     * @param controlType             过滤类型，明细、汇总、全部
     * @param employeeId              当前占用行员工ID
     * @param filtrateMethod          控制规则取值方式
     * @param controlEmpGroupCodeFrom 控制规则员工组代码从
     * @param controlEmpGroupCodeTo   控制规则员工组代码到
     * @return 符合的员工组
     * @author mouse 2019-01-10 15:50
     */
    List<ExpMoEmpGroup> checkExpMoEmpGroup(String controlType, Long employeeId, String filtrateMethod,
                                           String controlEmpGroupCodeFrom, String controlEmpGroupCodeTo);
}
