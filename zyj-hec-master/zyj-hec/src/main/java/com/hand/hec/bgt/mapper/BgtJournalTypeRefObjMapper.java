package com.hand.hec.bgt.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.bgt.dto.BgtJournalTypeRefObj;

import java.util.List;

/**
 * <p>
 * 预算日记账类型关联费用对象Mapper
 * </p>
 * 
 * @author mouse 2019/01/07 16:48
 */
public interface BgtJournalTypeRefObjMapper extends Mapper<BgtJournalTypeRefObj>{
    /**
     * 根据预算日记账类型ID查询关联费用对象
     *
     * @param bgtJournalTypeRefObj
     * @author guiyuting 2019-03-20 19:46
     * @return 
     */
    List<BgtJournalTypeRefObj> queryByBgtJournalType(BgtJournalTypeRefObj bgtJournalTypeRefObj);

}