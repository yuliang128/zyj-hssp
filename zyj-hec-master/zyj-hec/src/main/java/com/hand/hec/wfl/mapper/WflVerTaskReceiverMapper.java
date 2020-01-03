package com.hand.hec.wfl.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.wfl.dto.WflVerTaskRcvBizDetail;
import com.hand.hec.wfl.dto.WflVerTaskReceiver;

import java.util.List;

/**
 * description
 *
 * @author mouse 2019/02/20 15:46
 */
public interface WflVerTaskReceiverMapper extends Mapper<WflVerTaskReceiver> {

    /**
     *
     *
     * @param receiver
     * @author mouse 2019-02-20 16:18
     * @return java.util.List<com.hand.hec.wfl.dto.WflVerTaskReceiver>
     */
    List<WflVerTaskReceiver> selectVerTaskReceiver(WflVerTaskReceiver receiver);
}
