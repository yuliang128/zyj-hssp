package com.hand.hec.bgt.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.bgt.dto.BgtOrgPeriodStatus;

import java.util.List;

/**
 * <p>
 * 预算组织级预算期间控制mapper
 * </p>
 *
 * @author guiyuting 2019/03/14 18:39
 */
public interface BgtOrgPeriodStatusMapper extends Mapper<BgtOrgPeriodStatus>{
    /**
     * 预算组织级查询已打开的期间
     *
     * @param bgtOrgPeriodStatus
     * @author guiyuting 2019-03-14 18:35
     * @return 
     */
    List<BgtOrgPeriodStatus> queryBgtOrgOpenPeriod(BgtOrgPeriodStatus bgtOrgPeriodStatus);

    /**
     * 预算组织级查询已关闭的期间
     *
     * @param bgtOrgPeriodStatus
     * @author guiyuting 2019-03-14 18:35
     * @return
     */
    List<BgtOrgPeriodStatus> queryBgtOrgClosedPeriod(BgtOrgPeriodStatus bgtOrgPeriodStatus);

    /**
     * 更新预算组织期间，更改成关闭状态
     *
     * @param bgtOrgPeriodStatus
     * @author guiyuting 2019-03-15 11:05
     * @return
     */
    void updateOrgPeriod(BgtOrgPeriodStatus bgtOrgPeriodStatus);

}