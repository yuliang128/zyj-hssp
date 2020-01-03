package com.hand.hec.expm.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.expm.dto.ExpPolicyPlace;

import java.util.List;

public interface IExpPolicyPlaceService extends IBaseService<ExpPolicyPlace>, ProxySelf<IExpPolicyPlaceService>{

    /**
     * 页面初始查询
     * @param
     */
    List<ExpPolicyPlace> queryPolicyPlace(IRequest request, ExpPolicyPlace expPolicyPlace, int pageNum, int pageSize);
}