package com.hand.hec.bpm.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.bpm.dto.PageTag;

import java.util.List;

public interface PageTagMapper extends Mapper<PageTag> {
    List<PageTag> queryPageTag(Long layoutId);
}
