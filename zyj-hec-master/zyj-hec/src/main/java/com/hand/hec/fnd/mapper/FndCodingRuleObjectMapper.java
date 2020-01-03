package com.hand.hec.fnd.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.fnd.dto.FndCodingRuleObject;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FndCodingRuleObjectMapper extends Mapper<FndCodingRuleObject> {


    /**
     * 重写默认select 按照单据类型和优先级进行排序
     * 
     * @param record
     * @return
     */
    @Override
    List<FndCodingRuleObject> select(FndCodingRuleObject record);

    /**
     * 根据单据类别、类型、组织信息等获取单据编码
     *
     * @param docCategory
     * @param docType
     * @param magOrgId
     * @param companyId
     * @param accEntityId
     * @author mouse 2019-03-26 19:14
     * @return java.util.List<com.hand.hec.fnd.dto.FndCodingRuleObject>
     */
    List<FndCodingRuleObject> getMatchCodingRuleObject(@Param("docCategory") String docCategory,
                    @Param("docType") String docType, @Param("magOrgId") Long magOrgId,
                    @Param("companyId") Long companyId, @Param("accEntityId") Long accEntityId);
}
