package com.hand.hec.exp.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.exp.dto.ExpComEmpGroupRefEmp;

import java.util.List;

/**
 * <p>
 * 员工分配员工组Service
 * </p>
 *
 * @author xiuxian.wu 2019/01/25 14:48
 */
public interface IExpComEmpGroupRefEmpService extends IBaseService<ExpComEmpGroupRefEmp>, ProxySelf<IExpComEmpGroupRefEmpService> {
    /**
     * 查询所有已分配给员工的员工组
     *
     * @param dto            查询条件
     * @param page
     * @param pageSize
     * @param requestContext
     * @return 返回员工组
     * @author xiuxian.wu 2019-02-26 16:21
     */
    List<ExpComEmpGroupRefEmp> queryAll(IRequest requestContext, ExpComEmpGroupRefEmp dto, Integer page, Integer pageSize);
}