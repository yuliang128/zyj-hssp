package com.hand.hec.bgt.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.bgt.dto.BgtJournalType;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 预算日记账类型Service
 * </p>
 *
 * @author mouse 2019/01/07 17:00
 */
public interface IBgtJournalTypeService extends IBaseService<BgtJournalType>, ProxySelf<IBgtJournalTypeService> {


    /**
     * 根据预算日记账类型ID获取预算日记账类型
     *
     * @param request
     * @param journalTypeId
     * @author mouse 2019-03-14 18:12
     * @return com.hand.hec.bgt.dto.BgtJournalType
     */
    BgtJournalType getBgtJournalType(IRequest request, Long journalTypeId);

    /**
     * 获取预算日记账类型对应的费用对象信息
     *
     * @param bgtJournalTypeId
     * @param position
     * @author mouse 2019-03-21 13:52
     * @return java.util.List<java.util.Map>
     */
    List<Map> getJournalTypeObjInfo(IRequest request, Long bgtJournalTypeId, String position);

    /**
     * 根据预算日记账头ID查询预算日记账类型信息
     *
     * @param journalHeaderId 预算日记账头ID
     * @author guiyuting 2019-04-09 15:33
     * @return
     */
    BgtJournalType queryInfoByBgtJournal(Long journalHeaderId);

}
