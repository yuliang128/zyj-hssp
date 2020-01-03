package com.hand.hec.wfl.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.wfl.dto.WflVerGtwBizRuleDetail;

import java.util.List;

/**
 * description
 *
 * @author mouse 2019/02/20 15:27
 */
public interface WflVerGtwBizRuleDetailMapper extends Mapper<WflVerGtwBizRuleDetail>{

    /**
     * 查询已发布的网关权限组明细
     *
     * @param detail
     * @author mouse 2019-02-20 15:52
     * @return java.util.List<com.hand.hec.wfl.dto.WflVerGtwBizRuleDetail>
     */
    List<WflVerGtwBizRuleDetail> selectVerGtwBizRuleDetail(WflVerGtwBizRuleDetail detail);
}
