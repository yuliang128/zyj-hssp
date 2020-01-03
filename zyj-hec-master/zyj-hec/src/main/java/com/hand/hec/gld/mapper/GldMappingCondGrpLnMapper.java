package com.hand.hec.gld.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.gld.dto.GldMappingCondGrpLn;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GldMappingCondGrpLnMapper extends Mapper<GldMappingCondGrpLn>{

    /**
     *@Description 根据匹配组名称获取需要删除的行表数据
     *@Author hui.zhao01@hand-china.com
     *@Date 2019/1/22 10:34
     *@Param groupName 匹配组名称
     *@Version 1.0
     **/
    List<GldMappingCondGrpLn> selectGroupLn(@Param("groupName")String groupName);

    /**
     *@Description 获取匹配组行明细
     *@Author hui.zhao01@hand-china.com
     *@Date 2019/1/24 11:13
     *@Param groupName 匹配组名称
     *@Version 1.0
     **/
    List<GldMappingCondGrpLn> selectGroupLines(@Param("groupName")String groupName);

    /**
     *@Description 根据匹配组名称获取对应的匹配项
     *@Author hui.zhao01@hand-china.com
     *@Date 2019/2/15 14:03
     *@Param groupName 匹配组名称
     *@Version 1.0
     **/
    List<GldMappingCondGrpLn> getMappingCondGrpLns(@Param("groupName")String groupName);
}