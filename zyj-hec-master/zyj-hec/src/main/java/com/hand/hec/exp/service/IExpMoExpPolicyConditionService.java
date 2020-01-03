package com.hand.hec.exp.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.dto.CodeValue;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.exp.dto.ExpMoExpPolicyCondition;

import java.util.List;

/**
 * <p>
 * 政策标准明细条件Service
 * </p>
 *
 * @author xiuxian.wu 2019/01/21 10:20
 */

public interface IExpMoExpPolicyConditionService extends IBaseService<ExpMoExpPolicyCondition>, ProxySelf<IExpMoExpPolicyConditionService> {
    /**
     * 获取所有匹配条件代码
     *
     * @param requestContext
     * @param page
     * @param pageSize
     * @return 返回所有匹配条件代码
     * @author xiuxian.wu 2019/02/21 10:31
     */
    List<CodeValue> queryAllMatchingCondition(IRequest requestContext, Integer page, Integer pageSize);

    /**
     * 增加修改数据
     *
     * @param list    数据
     * @param request
     * @return 已经改变的数据
     * @author xiuxian.wu 2019-02-26 16:14
     */
    List<ExpMoExpPolicyCondition> submitHds(IRequest request, List<ExpMoExpPolicyCondition> list);
}