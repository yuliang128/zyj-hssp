package com.hand.hec.wfl.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.wfl.dto.WflVerSubProcessTask;
import com.hand.hec.wfl.dto.WflVerTaskRcvBizDetail;

import java.util.List;

/**
 * description
 *
 * @author mouse 2019/02/20 15:41
 */
public interface WflVerTaskRcvBizDetailMapper extends Mapper<WflVerTaskRcvBizDetail> {

    /**
     * 已发布的任务接受者权限规则明细
     *
     * @param detail
     * @author mouse 2019-02-20 16:16
     * @return java.util.List<com.hand.hec.wfl.dto.WflVerTaskRcvBizDetail>
     */
    List<WflVerTaskRcvBizDetail> selectVerTaskRcvBizDetail(WflVerTaskRcvBizDetail detail);
}
