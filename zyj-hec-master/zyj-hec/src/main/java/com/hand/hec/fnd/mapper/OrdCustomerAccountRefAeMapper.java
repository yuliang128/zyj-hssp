package com.hand.hec.fnd.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.fnd.dto.OrdCustomerAccountRefAe;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * OrdCustomerAccountRefAeMapper
 * </p>
 * 
 * @author guiyuting 2019/02/22 17:01
 */
public interface OrdCustomerAccountRefAeMapper extends Mapper<OrdCustomerAccountRefAe> {

    /**
     * 校验当前核算主体是否已作为同一客户下不同银行账号的主账号
     *
     * @param accEntityId 核算主体ID
     * @param accountId 账号ID
     * @param customerId 客户ID
     * @author guiyuting 2019-02-20 19:51
     * @return
     */
    int checkPrimaryOnly(@Param("accEntityId") Long accEntityId, @Param("accountId") Long accountId,
                    @Param("customerId") Long customerId);

    /**
     * 更新账户与核算主体关联信息
     *
     * @param accountRefAe
     * @author guiyuting 2019-02-21 11:04
     * @return null
     */

    void updateByAccEntity(OrdCustomerAccountRefAe accountRefAe);

}
