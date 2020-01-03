package com.hand.hec.bgt.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.bgt.dto.BgtJournalTypeRefObj;

import java.util.List;

/**
 * <p>
 * 预算日记账类型关联费用对象Service
 * </p>
 * 
 * @author mouse 2019/01/07 17:01
 */
public interface IBgtJournalTypeRefObjService extends IBaseService<BgtJournalTypeRefObj>, ProxySelf<IBgtJournalTypeRefObjService>{
    /**
     * 根据预算日记账类型ID查询关联费用对象
     *
     * @param bgtJournalTypeRefObj
     * @author guiyuting 2019-03-20 19:46
     * @return
     */
    List<BgtJournalTypeRefObj> queryByBgtJournalType(IRequest request, BgtJournalTypeRefObj bgtJournalTypeRefObj,int page,int pageSize);

}