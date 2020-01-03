package com.hand.hec.gld.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.gld.dto.GldSegmentSource;

public interface GldSegmentSourceMapper extends Mapper<GldSegmentSource>{
    /**
     * 获取科目段取值来源数据
     *@Author hui.zhao01@hand-china.com
     *@Date 2019/2/21 14:28
     *@param  segmentId 科目段定义表Id
     *@return  GlSegmentSource dto对象
     *@Version 1.0
     **/
    List<GldSegmentSource> getSegmentSource(@Param("segmentId")Long segmentId);
}