package com.hand.hec.bgt.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.bgt.dto.BgtEntityPeriodStatus;

import java.util.List;

/**
 * <p>
 * 预算实体级预算期间控制service
 * </p>
 *
 * @author guiyuting 2019/03/14 18:43
 */
public interface IBgtEntityPeriodStatusService
                extends IBaseService<BgtEntityPeriodStatus>, ProxySelf<IBgtEntityPeriodStatusService> {
    /**
     * 预算组织级查询已打开的期间
     *
     * @param bgtEntityPeriodStatus
     * @author guiyuting 2019-03-14 18:35
     * @return
     */
    List<BgtEntityPeriodStatus> queryBgtEntityOpenPeriod(IRequest request, BgtEntityPeriodStatus bgtEntityPeriodStatus);

    /**
     * 预算组织级查询已关闭的期间
     *
     * @param bgtEntityPeriodStatus
     * @author guiyuting 2019-03-14 18:35
     * @return
     */
    List<BgtEntityPeriodStatus> queryBgtEntityClosedPeriod(IRequest request,
                    BgtEntityPeriodStatus bgtEntityPeriodStatus);

    /**
     * 关闭预算实体的期间
     *
     * @param bgtEntityPeriodStatus
     * @author guiyuting 2019-03-15 10:33
     * @return
     */
    void closeEntityPeriod(IRequest request, BgtEntityPeriodStatus bgtEntityPeriodStatus);

    /**
     * 更新预算实体期间，更改成关闭状态
     *
     * @param bgtEntityPeriodStatus
     * @author guiyuting 2019-03-15 11:05
     * @return
     */
    void updateEntityPeriod(IRequest request, BgtEntityPeriodStatus bgtEntityPeriodStatus);

    /**
     * 关闭预算实体的期间
     *
     * @param bgtEntityPeriodStatus
     * @author guiyuting 2019-03-15 10:33
     * @return
     */
    void openEntityPeriod(IRequest request, BgtEntityPeriodStatus bgtEntityPeriodStatus);

}
