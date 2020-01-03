package com.hand.hec.bgt.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.bgt.dto.BgtControlRuleDetail;

import java.util.List;

/**
 * <p>
 * 预算控制规则明细Mapper
 * </p>
 * 
 * @author mouse 2019/01/07 16:47
 */
public interface BgtControlRuleDetailMapper extends Mapper<BgtControlRuleDetail>{

    /**
     * 预算控制规则定义明细信息查询
     *
     * @param bgtControlRuleDetail 明细信息
     * @author guiyuting 2019-03-07 17:16
     * @return 符合条件的明细信息
     */
    List<BgtControlRuleDetail> queryByRuleId(BgtControlRuleDetail bgtControlRuleDetail);

}