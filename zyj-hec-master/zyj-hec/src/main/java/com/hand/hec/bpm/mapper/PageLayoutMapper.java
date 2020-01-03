package com.hand.hec.bpm.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.bpm.dto.PageLayout;

import java.util.List;

public interface PageLayoutMapper extends Mapper<PageLayout>{
    List<PageLayout> queryByPageId(PageLayout PageLayout);
}