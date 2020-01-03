package com.hand.hec.bpm.mapper;

import com.hand.hec.bpm.dto.TpltButton;
import com.hand.hap.mybatis.common.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface TpltButtonMapper extends Mapper<TpltButton> {
    List<TpltButton> queryByTemplateId(TpltButton bpmTpltButtons);

    List<Map> checkTemplateId(@Param("templateId") Long templateId);

    List<TpltButton> queryByParas(TpltButton bpmTpltButtons);
}