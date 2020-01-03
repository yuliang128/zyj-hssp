package com.hand.hec.bpm.mapper;

import java.util.HashMap;
import java.util.List;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.bpm.dto.Template;
import org.apache.ibatis.annotations.Param;

public interface TemplateMapper extends Mapper<Template> {
	List<Template> selectAllByLike(Template bpmTemplatesDTO);

	List<HashMap> getPageGroup(@Param("templateId") Long templateId);

	List<Template> queryTemplateLov(Template bpmTemplates);

	List<Template> queryDatesetFetch(@Param("pageId") Long pageId);

}