package com.hand.hec.exp.service;

import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.exp.dto.ExpMoExpPolicyCondHds;

import java.util.Map;

/**
 * <p>
 * 政策标准明细条件头Service
 * </p>
 *
 * @author xiuxian.wu 2019-02-21
 */
public interface IExpMoExpPolicyCondHdsService extends IBaseService<ExpMoExpPolicyCondHds>, ProxySelf<IExpMoExpPolicyCondHdsService> {
    /**
     * 查询组件是否显示
     *
     * @param conditionId 政策标准明细条件ID
     * @return 返回需要显示的组件的状态
     * @author xiuxian.wu 2019-02-22 16:01
     */
    Map<String, String> queryAllMatchItemCode(Long conditionId);
}