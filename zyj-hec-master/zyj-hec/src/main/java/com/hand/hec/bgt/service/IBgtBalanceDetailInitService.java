package com.hand.hec.bgt.service;

/**
 * <p>
 * 预算保留临时表 service
 * </p>
 * 
 * @author guiyuting 2019/05/22 15:53
 */
import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.bgt.dto.BgtBalanceDetailInit;

import java.util.List;

public interface IBgtBalanceDetailInitService
                extends IBaseService<BgtBalanceDetailInit>, ProxySelf<IBgtBalanceDetailInitService> {

    /**
     * 根据sessionId删除预算保留临时表记录
     *
     * @param reserveFlag R:申请单冻结,U:报销单占用
     * @author guiyuting 2019-05-22 15:40
     * @return
     */
    void deleteBgtBalanceDetailInit(IRequest request, String reserveFlag);

    /**
     * 新增预算保留临时表记录
     *
     * @param bgtBalanceDetailInit
     * @author guiyuting 2019-05-22 15:52
     * @return
     */
    void insertBgtBalanceDetailInit(IRequest request, BgtBalanceDetailInit bgtBalanceDetailInit);

    /**
     * 预算余额查询结果 明细处理
     *
     * @param bgtBalanceDetailInit
     * @author guiyuting 2019-05-22 16:03
     * @return
     */
    boolean downDeal(IRequest request, List<BgtBalanceDetailInit> list);

    /**
     * 查询预算余额明细
     *
     * @param bgtBalanceDetailInit
     * @author guiyuting 2019-05-23 10:41
     * @return
     */
    List<BgtBalanceDetailInit> queryBgtBalanceInit(IRequest request, BgtBalanceDetailInit bgtBalanceDetailInit,
                    int page, int pageSize);

}
