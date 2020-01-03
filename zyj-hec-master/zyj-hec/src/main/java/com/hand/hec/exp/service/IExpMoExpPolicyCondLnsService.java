package com.hand.hec.exp.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.exp.dto.ExpMoExpPolicyCondLns;

import java.util.List;

/**
 * <p>
 * 政策标准明细条件行Service
 * </p>
 *
 * @author xiuxian.wu 2019-02-21
 */
public interface IExpMoExpPolicyCondLnsService extends IBaseService<ExpMoExpPolicyCondLns>, ProxySelf<IExpMoExpPolicyCondLnsService> {
    /**
     * 查询标准明细条件所有的数据
     *
     * @param request
     * @param condition
     * @param pageNum
     * @param condition 查询条件
     * @return 返回所有明细条件数据
     * @author xiuxian.wu 2019-02-21 16:26
     */

    List<ExpMoExpPolicyCondLns> queryPolicyCondLns(IRequest request, ExpMoExpPolicyCondLns condition, int pageNum, int pageSize);

    /**
     * 保存政策标准标准明细条件数据
     *
     * @param request
     * @param conditions
     * @return 已保存的政策标准标准明细条件数据
     * @author xiuxian.wu 2019/02/22 15:14
     */
    List<ExpMoExpPolicyCondLns> batchSubmit(IRequest request, List<ExpMoExpPolicyCondLns> conditions);
}