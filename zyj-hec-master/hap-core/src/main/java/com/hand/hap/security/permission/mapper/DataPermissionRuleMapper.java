package com.hand.hap.security.permission.mapper;

import java.util.List;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hap.security.permission.dto.DataPermissionRule;

/**
 * @author jialong.zuo@hand-china.com on 2017/12/8
 */
public interface DataPermissionRuleMapper extends Mapper<DataPermissionRule> {

    /** 获取未被选择的规则
     * @param dataPermissionRule
     * @return
     */
    List<DataPermissionRule> selectRuleWithoutTableSelect(DataPermissionRule dataPermissionRule);

}