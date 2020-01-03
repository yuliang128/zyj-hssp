package com.hand.hec.gld.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.gld.dto.GldMappingCondGrpHd;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

public interface GldMappingCondGrpHdMapper extends Mapper<GldMappingCondGrpHd>{
    /**
     *@Description 获取最大优先级
     *@Author hui.zhao01@hand-china.com
     *@Date 2019/1/18 11:24
     *@Param
     *@Version 1.0
     **/
    Long getMaxPriority(@Param("usageCode") String usageCode);

    /**
     *@Description 校验对应的优先级是否存在
     *@Author hui.zhao01@hand-china.com
     *@Date 2019/1/18 14:08
     *@Param usageCode 用途代码编码
     *@Param priority 优先级
     *@Version 1.0
     **/
    Integer checkUnique(@Param("usageCode") String usageCode,@Param("priority") Long priority);

    /**
     *@Description 创建用途代码对应匹配组对应的表
     *@Author hui.zhao01@hand-china.com
     *@Date 2019/1/18 15:23
     *@Param createTableSql 拼接的sql语句
     *@Version 1.0
     **/
    void executeSql(@Param("createTableSql") String createTableSql);

    /**
     *@Description 获取动态ds查询结果
     *@Author hui.zhao01@hand-china.com
     *@Date 2019/1/22 15:29
     *@Param tableName 动态表明
     *@Param whereClause 动态筛选条件
     *@Param setOfBooksId 账套ID
     *@Param magOrgId 管理组织ID
     *@Param accountCode 科目代码
     *@Version 1.0
     **/
    List<HashMap<String,Object>> queryResult(@Param("tableName")String tableName,
                                             @Param("whereClause")String whereClause,
                                             @Param("setOfBooksId")Long setOfBooksId,
                                             @Param("magOrgId")Long magOrgId,
                                             @Param("accountCode")String accountCode);

    /**
     *@Description 根据用途代码获取所有匹配组数据
     *@Author hui.zhao01@hand-china.com
     *@Date 2019/2/15 13:55
     *@Param
     *@Version 1.0
     **/
    List<GldMappingCondGrpHd> getMappingCondGrpHds(@Param("usageCode")String usageCode);
}