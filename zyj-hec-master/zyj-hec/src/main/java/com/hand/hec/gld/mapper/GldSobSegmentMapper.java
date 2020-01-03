package com.hand.hec.gld.mapper;

import java.util.List;
import java.util.Map;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.gld.dto.GldSobSegment;
import org.apache.ibatis.annotations.Param;

public interface GldSobSegmentMapper extends Mapper<GldSobSegment>{
    /**
     * 根据segment字段名称获取对应的数据
     *@Author hui.zhao01@hand-china.com
     *@Date 2019/2/20 19:05
     *@param  segmentField segment字段
     *@param drCrFlag 借贷方标识
     *@param setOfBooksId 账套Id
     *@return  GldSobSegment
     *@Version 1.0
     **/
    GldSobSegment selectSegInfo(@Param("segmentField")String segment_field,@Param("drCrFlag")String drCrFlag,@Param("setOfBooksId")Long setOfBooksId);

    /**
     * 执行自定义sql
     *@Author hui.zhao01@hand-china.com
     *@Date 2019/2/21 11:09
     *@param  querySql sql语句
     *@return   String
     *@Version 1.0
     **/
    String executeSql(@Param("querySql")String querySql);

    /**
     * 查询segment段描述
     *
     * @param
     * @author dingwei.ma@hand-china.com 2019-02-25 16:00
     * @return List<Map>
     */
    List<Map> querySegmentDesc();
}