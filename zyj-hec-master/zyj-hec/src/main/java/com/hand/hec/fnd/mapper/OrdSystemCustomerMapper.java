package com.hand.hec.fnd.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.fnd.dto.OrdSystemCustomer;

import java.util.List;

/**
 * <p>
 * OrdSystemCustomerMapper
 * </p>
 * 
 * @author guiyuting 2019/02/22 17:00
 */
public interface OrdSystemCustomerMapper extends Mapper<OrdSystemCustomer>{

    /**
     * 根据核算主体查询客户信息
     *
     * @param ordSystemCustomer
     * @author guiyuting 2019-02-20 16:43
     * @return 客户信息
     */
    List<OrdSystemCustomer> queryByAccEntity(OrdSystemCustomer ordSystemCustomer);

}