package com.hand.hec.gld.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.gld.dto.GldUsageCodeCatalog;
import org.apache.ibatis.annotations.Param;

public interface GldUsageCodeCatalogMapper extends Mapper<GldUsageCodeCatalog>{

    /**
     *@Description 获取用途代码目录
     *@Author hui.zhao01@hand-china.com
     *@Date 2019/1/21 9:59
     *@Param mappingConditionCode 匹配项编码
     *@Param usageCode 用途代码编码
     *@Version 1.0
     **/
    GldUsageCodeCatalog selectUsageCodeCataLog(@Param("mappingConditionCode")String mappingConditionCodes,@Param("usageCode")String usageCode);
}