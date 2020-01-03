package com.hand.hec.wfl.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.wfl.dto.WflVerGtwBizRuleDetail;
import com.hand.hec.wfl.dto.WflVerGtwBizRuleGroup;

import java.util.List;

/**
 * description
 *
 * @author mouse 2019/02/20 15:29
 */
public interface WflVerGtwBizRuleGroupMapper extends Mapper<WflVerGtwBizRuleGroup>{

    /**
     * 查询已发布的网关权限组
     *
     * @param group
     * @author mouse 2019-02-20 15:53
     * @return java.util.List<com.hand.hec.wfl.dto.WflVerGtwBizRuleGroup>
     */
    List<WflVerGtwBizRuleGroup> selectVerGtwBizRuleGroup(WflVerGtwBizRuleGroup group);
}
