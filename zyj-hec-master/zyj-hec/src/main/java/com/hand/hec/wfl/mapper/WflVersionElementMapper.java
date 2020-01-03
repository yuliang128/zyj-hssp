package com.hand.hec.wfl.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.wfl.dto.WflVerGtwBizRuleGroup;
import com.hand.hec.wfl.dto.WflVersionElement;

import java.util.List;

/**
 * description
 *
 * @author mouse 2019/02/20 15:30
 */
public interface WflVersionElementMapper extends Mapper<WflVersionElement>{

    /**
     * 查询已发布的工作流元素
     *
     * @author mouse 2019-02-20 15:54
     * @return java.util.List<com.hand.hec.wfl.dto.WflVersionElement>
     */
    List<WflVersionElement> selectVersionElement();
}
