package com.hand.hec.wfl.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.wfl.dto.WflVersionProcess;
import com.hand.hec.wfl.dto.WflVersionTask;

import java.util.List;

/**
 * description
 *
 * @author mouse 2019/02/20 15:35
 */
public interface WflVersionTaskMapper extends Mapper<WflVersionTask> {

    /**
     * 查询已经发布的任务
     *
     * @author mouse 2019-02-20 16:02
     * @return java.util.List<com.hand.hec.wfl.dto.WflVersionTask>
     */
    List<WflVersionTask> selectVersionTask();
}
