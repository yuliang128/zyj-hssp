package com.hand.hap.flexfield.service;

import java.util.List;

import com.hand.hap.core.ProxySelf;
import com.hand.hap.flexfield.dto.FlexModel;
import com.hand.hap.system.service.IBaseService;

public interface IFlexModelService extends IBaseService<FlexModel>, ProxySelf<IFlexModelService> {

    /**删除弹性域模型
     * @param models 需要删除的FlexModel
     */
    void deleteFlexModel(List<FlexModel> models);

}