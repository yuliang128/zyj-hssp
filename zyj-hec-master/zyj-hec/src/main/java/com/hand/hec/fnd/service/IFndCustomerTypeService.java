package com.hand.hec.fnd.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.fnd.dto.FndCustomerType;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 客户类型定义Service
 * </p>
 *
 * @author xiuxian.wu 2019/02/14 11:07
 */
public interface IFndCustomerTypeService extends IBaseService<FndCustomerType>, ProxySelf<IFndCustomerTypeService> {

    /**
     * 根据核算主体查询客户类型
     *
     * @param accEntityId 核算主体ID
     * @author guiyu 2019-01-21 15:18
     * @return 符合条件的核算主体
     */
    List<FndCustomerType> queryByAccEntity(IRequest request, Long accEntityId);

}
