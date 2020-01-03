package com.hand.hec.wfl.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.wfl.dto.WflVersionEvent;
import com.hand.hec.wfl.dto.WflVersionFlow;

import java.util.List;

/**
 * description
 *
 * @author mouse 2019/02/20 15:33
 */
public interface WflVersionFlowMapper extends Mapper<WflVersionFlow> {

    /**
     * 查询已发布的工作流流向
     *
     * @author mouse 2019-02-20 15:56
     * @return java.util.List<com.hand.hec.wfl.dto.WflVersionFlow>
     */
    List<WflVersionFlow> selectVersionFlow();
}
