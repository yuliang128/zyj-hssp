package com.hand.hec.bgt.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.bgt.dto.BgtBalanceQueryGroup;
import com.hand.hec.bgt.dto.BgtJournalBalanceInit;
import org.apache.ibatis.annotations.Param;

public interface IBgtJournalBalanceInitService extends IBaseService<BgtJournalBalanceInit>, ProxySelf<IBgtJournalBalanceInitService>{

    /**
     * 初始化预算余额数据
     *
     * @param group
     * @author mouse 2019-04-18 11:08
     * @return void
     */
    void initBalanceData(IRequest request, @Param("group") BgtBalanceQueryGroup group);

    /**
     * 清理预算余额数据
     *
     *
     * @author mouse 2019-04-18 11:08
     * @return void
     */
    void clearBalanceData(IRequest request);
}