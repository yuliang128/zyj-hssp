package com.hand.hec.exp.service;

import java.util.List;

import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.exp.dto.ExpPositionGroup;

public interface IExpPositionGroupService extends IBaseService<ExpPositionGroup>, ProxySelf<IExpPositionGroupService> {

    /**
     * 预算检查的岗位组符合判断
     *
     * @param controlType 过滤类型，明细、汇总、全部
     * @param positionId 当前占用行岗位ID
     * @param filtrateMethod 控制规则取值方式
     * @param controlPositionGroupCodeFrom 控制规则岗位组代码从
     * @param controlPositionGroupCodeTo 控制规则岗位组代码到
     * @author mouse 2019-01-10 15:50
     * @return 符合的岗位组
     */
    List<ExpPositionGroup> checkExpPositionGroup(String controlType, Long positionId, String filtrateMethod,
            String controlPositionGroupCodeFrom, String controlPositionGroupCodeTo);

}
