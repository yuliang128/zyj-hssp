package com.hand.hec.gld.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.gld.dto.GldMappingCondition;
import org.apache.ibatis.annotations.Param;

public interface GldMappingConditionMapper extends Mapper<GldMappingCondition>{
    /**
     *@Description 获取科目ID
     *@Author hui.zhao01@hand-china.com
     *@Date 2019/2/15 15:16
     *@Param sqlText
     *@Version 1.0
     **/
    Long getAccount(@Param("sqlText")String sqlText);
}