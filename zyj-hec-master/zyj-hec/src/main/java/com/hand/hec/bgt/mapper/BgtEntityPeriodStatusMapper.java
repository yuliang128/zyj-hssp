package com.hand.hec.bgt.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.bgt.dto.BgtEntityPeriodStatus;

import java.util.List;

/**
 * <p>
 * 预算实体级预算期间控制mapper
 * </p>
 *
 * @author guiyuting 2019/03/14 18:39
 */
public interface BgtEntityPeriodStatusMapper extends Mapper<BgtEntityPeriodStatus>{

    /**
     * 预算组织级查询已打开的期间
     *
     * @param bgtEntityPeriodStatus
     * @author guiyuting 2019-03-14 18:35
     * @return
     */
    List<BgtEntityPeriodStatus> queryBgtEntityOpenPeriod(BgtEntityPeriodStatus bgtEntityPeriodStatus);

    /**
     * 预算实体级查询已关闭的期间
     *
     * @param bgtEntityPeriodStatus
     * @author guiyuting 2019-03-14 18:35
     * @return
     */
    List<BgtEntityPeriodStatus> queryBgtEntityClosedPeriod(BgtEntityPeriodStatus bgtEntityPeriodStatus);

    /**
     * 更新预算实体期间，更改成关闭状态
     *
     * @param bgtEntityPeriodStatus
     * @author guiyuting 2019-03-15 11:05
     * @return 
     */
    void updateEntityPeriod(BgtEntityPeriodStatus bgtEntityPeriodStatus);

}