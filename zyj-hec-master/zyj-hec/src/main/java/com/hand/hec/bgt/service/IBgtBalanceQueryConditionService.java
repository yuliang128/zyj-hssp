package com.hand.hec.bgt.service;

import com.hand.hap.core.IRequest;
import com.hand.hec.bgt.dto.BgtBalanceQueryCondition;
import com.hand.hec.bgt.dto.BgtBalanceQueryGroup;

import java.util.Map;

/**
 * description
 *
 * @author mouse 2019/04/18 11:18
 */
public interface IBgtBalanceQueryConditionService {

    /**
     * 根据期间进行数据过滤
     *
     * @param group
     * @author mouse 2019-04-18 11:27
     * @return void
     */
    void filterPeriod(IRequest request, BgtBalanceQueryGroup group);

    /**
     * 根据某个控制条件进行数据过滤
     *
     * @param condition
     * @author mouse 2019-04-18 11:21
     * @return void
     */
    void filterCondition(IRequest request, BgtBalanceQueryGroup group, BgtBalanceQueryCondition condition,
                    StringBuffer filterFieldSb);

    /**
     * 将某个控制字段扩展到上级或者对应的类型
     *
     * @param request
     * @param group
     * @param condition
     * @author mouse 2019-04-23 18:24
     * @return void
     */
    void extendCondition(IRequest request, BgtBalanceQueryGroup group,
                    Map<String, BgtBalanceQueryCondition> conditionMap, StringBuffer groupFieldSb);
}
