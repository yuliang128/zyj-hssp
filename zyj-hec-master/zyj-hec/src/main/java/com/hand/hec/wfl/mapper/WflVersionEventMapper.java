package com.hand.hec.wfl.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.wfl.dto.WflVersionElement;
import com.hand.hec.wfl.dto.WflVersionEvent;

import java.util.List;

/**
 * description
 *
 * @author mouse 2019/02/20 15:32
 */
public interface WflVersionEventMapper extends Mapper<WflVersionEvent>{

    /**
     * 查询已发布的工作流事件
     *
     * @author mouse 2019-02-20 15:55
     * @return java.util.List<com.hand.hec.wfl.dto.WflVersionEvent>
     */
    List<WflVersionEvent> selectVersionEvent();
}
