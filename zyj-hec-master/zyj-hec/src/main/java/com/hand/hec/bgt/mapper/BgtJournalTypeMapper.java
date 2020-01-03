package com.hand.hec.bgt.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.bgt.dto.BgtJournalType;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 预算日记账类型Mapper
 * </p>
 * 
 * @author mouse 2019/01/07 16:48
 */
public interface BgtJournalTypeMapper extends Mapper<BgtJournalType> {

    /**
     * 获取预算日记账类型对应的费用对象信息
     *
     * @param bgtJournalTypeId
     * @param position
     * @author mouse 2019-03-21 13:52
     * @return java.util.List<java.util.Map>
     */
    List<Map> getJournalTypeObjInfo(@Param("bgtJournalTypeId") Long bgtJournalTypeId,
                    @Param("position") String position);

    /**
     * 根据预算日记账头ID查询预算日记账类型信息
     *
     * @param journalHeaderId 预算日记账头ID
     * @author guiyuting 2019-04-09 15:33
     * @return 
     */
    BgtJournalType queryInfoByBgtJournal(@Param("journalHeaderId") Long journalHeaderId);
}
