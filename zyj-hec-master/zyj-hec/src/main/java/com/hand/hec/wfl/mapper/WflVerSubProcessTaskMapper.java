package com.hand.hec.wfl.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.wfl.dto.WflVerSubProcessTask;
import com.hand.hec.wfl.dto.WflVersionUserTask;

import java.util.List;

/**
 * description
 *
 * @author mouse 2019/02/20 15:38
 */
public interface WflVerSubProcessTaskMapper extends Mapper<WflVerSubProcessTask> {

    /**
     * 查询已发布的子流程任务
     *
     * @param subProcessTask
     * @author mouse 2019-02-20 16:15
     * @return java.util.List<com.hand.hec.wfl.dto.WflVerSubProcessTask>
     */
    List<WflVerSubProcessTask> selectVerSubProcessTask(WflVerSubProcessTask subProcessTask);
}
