package com.hand.hec.fnd.service;

import java.util.List;

import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.fnd.dto.FndDimensionValue;

public interface IFndDimensionValueService
        extends IBaseService<FndDimensionValue>, ProxySelf<IFndDimensionValueService> {

    /**
     * 预算检查的维度符合判断
     *
     * @param controlType 过滤类型，明细、汇总、全部
     * @param dimensionId 维度ID
     * @param filtrateMethod 控制规则取值方式
     * @param dimensionValueId 维度值ID
     * @param controlDimValueCodeFrom 控制规则维度值代码从
     * @param controlDimValueCodeTo 控制规则维度值代码到
     * @author mouse 2019-01-10 15:50
     * @return 符合的维值
     */

    List<FndDimensionValue> checkFndDimension(String controlType, Long dimensionId, String filtrateMethod,
            Long dimensionValueId, String controlDimValueCodeFrom, String controlDimValueCodeTo);
}
