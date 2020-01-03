package com.hand.hec.wfl.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.wfl.dto.WflVersionTask;
import com.hand.hec.wfl.dto.WflVersionUserTask;

import java.util.List;

/**
 * description
 *
 * @author mouse 2019/02/20 15:47
 */
public interface WflVersionUserTaskMapper extends Mapper<WflVersionUserTask> {

    /**
     * 查询已发布的用户任务
     *
     * @param userTask
     * @author mouse 2019-02-20 16:15
     * @return java.util.List<com.hand.hec.wfl.dto.WflVersionUserTask>
     */
    List<WflVersionUserTask> selectVerUserTask(WflVersionUserTask userTask);
}
