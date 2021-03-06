package com.hand.hap.flexfield.service;

import java.util.List;

import com.hand.hap.core.ProxySelf;
import com.hand.hap.flexfield.dto.FlexRuleField;
import com.hand.hap.system.service.IBaseService;

public interface IFlexRuleFieldService extends IBaseService<FlexRuleField>, ProxySelf<IFlexRuleFieldService> {

    /**查询弹性域规则Field
     * @param flexRuleField 查询参数
     * @return 相应的FlexRuleField
     */
    List<FlexRuleField> queryFlexRuleField(FlexRuleField flexRuleField);

}