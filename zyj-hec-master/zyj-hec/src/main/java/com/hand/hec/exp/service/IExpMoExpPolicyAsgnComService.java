package com.hand.hec.exp.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.exp.dto.ExpMoExpPolicyAsgnCom;

import java.util.List;

/**
 * <p>
 * 政策标准关联管理公司Service
 * </p>
 *
 * @author xiuxian.wu 2019/01/19 25:25
 */
public interface IExpMoExpPolicyAsgnComService extends IBaseService<ExpMoExpPolicyAsgnCom>, ProxySelf<IExpMoExpPolicyAsgnComService> {
    /**
     * 查找分配给政策标准的管理公司
     *
     * @param condition 查找条件
     * @param pageNum
     * @param pageSize
     * @param request
     * @return 返回剩余管理公司信息
     * @author xiuxian.wu 2019/02/20 15:14
     */
    List<ExpMoExpPolicyAsgnCom> queryCompanyByExpensePolicyId(IRequest request, ExpMoExpPolicyAsgnCom condition, int pageNum, int pageSize);

    /**
     * 查找剩余可以分配给政策标准的管理公司
     *
     * @param condition 查找条件
     * @param pageNum
     * @param pageSize
     * @param request
     * @return 返回剩余管理公司信息
     * @author xiuxian.wu 2019/02/20 15:14
     */
    List<ExpMoExpPolicyAsgnCom> queryRemainingCompanyByExpensePolicyId(IRequest request, ExpMoExpPolicyAsgnCom condition, int pageNum, int pageSize);
}