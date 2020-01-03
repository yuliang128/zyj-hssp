package com.hand.hec.bgt.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.bgt.dto.BgtPeriodStatus;
import org.apache.poi.ss.formula.functions.T;

import java.util.List;

/**
 * <p>
 * 预算期间状态Service
 * </p>
 * 
 * @author mouse 2019/01/07 17:03
 */
public interface IBgtPeriodStatusService extends IBaseService<BgtPeriodStatus>, ProxySelf<IBgtPeriodStatusService> {

    /**
     * 根据预算实体ID 是否存在，查询开启的预算管理级的预算期间控制，或预算实体级的预算期间控制
     *
     * @param bgtPeriodStatus
     * @author guiyuting 2019-03-14 19:11
     * @return
     */
    List queryOpenPeriod(IRequest request, BgtPeriodStatus bgtPeriodStatus, int page, int pageSize);

    /**
     * 根据预算实体ID 是否存在，查询关闭的预算管理级的预算期间控制，或预算实体级的预算期间控制
     *
     * @param bgtPeriodStatus
     * @author guiyuting 2019-03-14 19:11
     * @return
     */
    List queryClosePeriod(IRequest request, BgtPeriodStatus bgtPeriodStatus, int page, int pageSize);

    /**
     * 根据预算实体ID 是否存在,关闭预算组织下的期间，或预算实体下的期间
     *
     * @param bgtPeriodStatus
     * @author guiyuting 2019-03-15 10:30
     * @return 
     */
    boolean closePeriod(IRequest request,BgtPeriodStatus bgtPeriodStatus);

    /**
     * 根据预算实体ID 是否存在,打开预算组织下的期间，或预算实体下的期间
     *
     * @param bgtPeriodStatus
     * @author guiyuting 2019-03-15 10:30
     * @return
     */
    boolean openPeriod(IRequest request,BgtPeriodStatus bgtPeriodStatus);
}
