package com.hand.hec.fnd.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.fnd.dto.FndCodingRuleObject;

import java.util.List;

public interface IFndCodingRuleObjectService
                extends IBaseService<FndCodingRuleObject>, ProxySelf<IFndCodingRuleObjectService> {

    /**
     * 根据单据类别、类型、组织信息等获取单据编码
     *
     * @param docCategory
     * @param docType
     * @param magOrgId
     * @param companyId
     * @param accEntityId
     * @author mouse 2019-03-26 19:11
     * @return java.lang.String
     */
    String getRuleCode(String docCategory, String docType, Long magOrgId, Long companyId, Long accEntityId);

}
