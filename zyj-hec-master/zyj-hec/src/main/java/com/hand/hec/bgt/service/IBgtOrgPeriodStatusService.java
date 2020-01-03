package com.hand.hec.bgt.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.bgt.dto.BgtOrgPeriodStatus;

import java.util.List;

/**
 * <p>
 * 预算组织级预算期间控制service
 * </p>
 * 
 * @author guiyuting 2019/03/14 18:43
 */
public interface IBgtOrgPeriodStatusService
                extends IBaseService<BgtOrgPeriodStatus>, ProxySelf<IBgtOrgPeriodStatusService> {

    /**
     * 预算组织级查询已打开的期间
     *
     * @param bgtOrgPeriodStatus
     * @author guiyuting 2019-03-14 18:35
     * @return
     */
    List<BgtOrgPeriodStatus> queryBgtOrgOpenPeriod(IRequest request, BgtOrgPeriodStatus bgtOrgPeriodStatus);

    /**
     * 预算组织级查询已关闭的期间
     *
     * @param bgtOrgPeriodStatus
     * @author guiyuting 2019-03-14 18:35
     * @return
     */
    List<BgtOrgPeriodStatus> queryBgtOrgClosedPeriod(IRequest request, BgtOrgPeriodStatus bgtOrgPeriodStatus);

    /**
     * 关闭预算组织的期间
     *
     * @param bgtOrgPeriodStatus
     * @author guiyuting 2019-03-15 10:32
     * @return
     */
    void closeOrgPeriod(IRequest request, BgtOrgPeriodStatus bgtOrgPeriodStatus);

    /**
     * 更新预算组织期间，更改成关闭状态
     *
     * @param bgtOrgPeriodStatus
     * @author guiyuting 2019-03-15 11:05
     * @return
     */
    void updateOrgPeriod(IRequest request, BgtOrgPeriodStatus bgtOrgPeriodStatus);

    /**
     * 打开预算组织的期间
     *
     * @param bgtOrgPeriodStatus
     * @author guiyuting 2019-03-15 10:32
     * @return
     */
    void openOrgPeriod(IRequest request, BgtOrgPeriodStatus bgtOrgPeriodStatus);



}
