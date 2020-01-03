package com.hand.hec.bpm.mapper;

import com.hand.hap.core.IRequest;
import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.bpm.dto.Page;
import org.apache.ibatis.annotations.Param;
import uncertain.composite.CompositeMap;

import java.util.List;
import java.util.Map;

public interface PageMapper extends Mapper<Page> {
    public Map queryTemplateReference(IRequest iRequest, @Param("pageId") Long pageId);

}