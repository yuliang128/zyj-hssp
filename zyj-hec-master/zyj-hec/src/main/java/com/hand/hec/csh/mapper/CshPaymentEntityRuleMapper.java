package com.hand.hec.csh.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.csh.dto.CshPaymentEntityRule;
import org.apache.ibatis.annotations.Param;

public interface CshPaymentEntityRuleMapper extends Mapper<CshPaymentEntityRule> {
    /**
     *
     *
     * @param
     * @author user 2019-01-23 10:19
     * @return
     */
    Long selectForAccEntityId(@Param("magOrgId") Long magOrgId, @Param("docCategory") String docCategory,
                    @Param("docCompanyId") Long docCompanyId, @Param("docTypeId") Long docTypeId,
                    @Param("paymentMethodId") Long paymentMethodId, @Param("payeeCategory") String payeeCategory);

}
