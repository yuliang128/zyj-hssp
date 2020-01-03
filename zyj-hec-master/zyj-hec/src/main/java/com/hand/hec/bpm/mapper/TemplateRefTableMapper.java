package com.hand.hec.bpm.mapper;

import com.hand.hec.bpm.dto.TemplateRefTable;
import com.hand.hap.mybatis.common.Mapper;

import java.util.List;

public interface TemplateRefTableMapper extends Mapper<TemplateRefTable>{
    List<TemplateRefTable> queryByTemplateId(TemplateRefTable TemplateRefTable);
}