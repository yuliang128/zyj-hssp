package com.hand.hec.gld.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.gld.dto.GldMappingConditionSql;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GldMappingConditionSqlMapper extends Mapper<GldMappingConditionSql> {
    /**
     * 获取匹配项明细
     * 
     * @Author hui.zhao01@hand-china.com
     * @Date 2019/1/21 10:21
     * @param mappingConditionCode 匹配项编码
     * @return GldMappingConditionSql
     * @Version 1.0
     **/
    GldMappingConditionSql selectMappingConditionDetail(@Param("mappingConditionCode") String mappingConditionCode);

    /**
     * 获取表字段类型
     * 
     * @Author hui.zhao01@hand-china.com
     * @Date 2019/1/21 10:45
     * @param refTable 关联表
     * @param refField 关联字段
     * @param dbType 数据库类型
     * @return String
     * @Version 1.0
     **/
    String getDataType(@Param("refTable") String refTable, @Param("refField") String refField,
                    @Param("dbType") String dbType);

    /**
     * 创建匹配组时匹配项grid数据
     *
     * @Author hui.zhao01@hand-china.com
     * @Date 2019/4/11 16:14
     * @param dto
     * @return
     * @Version 1.0
     **/
    List<GldMappingConditionSql> selectGridData( @Param("usageCode") String usageCode);
}
