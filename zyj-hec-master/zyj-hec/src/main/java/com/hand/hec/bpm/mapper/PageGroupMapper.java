package com.hand.hec.bpm.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.bpm.dto.PageGroup;

import java.util.List;

public interface PageGroupMapper extends Mapper<PageGroup> {

    List<PageGroup> queryByCondition(PageGroup group);

//    List<Map> queryPageGroupEditor(@Param("pageId") Long pageId);
}