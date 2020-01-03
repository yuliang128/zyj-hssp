package com.hand.hec.bgt.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.bgt.dto.BgtJournalLine;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 预算日记账行Mapper
 * </p>
 * 
 * @author mouse 2019/01/07 16:48
 */
public interface BgtJournalLineMapper extends Mapper<BgtJournalLine>{

    /**
     * 根据预算日记账头ID获取所有行信息
     *
     * @param journalHeaderId 预算日记账头ID
     * @author guiyuting 2019-03-28 15:07
     * @return 预算日记账行信息
     */
    List<BgtJournalLine> queryInfoByHeaderId(@Param("journalHeaderId") Long journalHeaderId);

    /**
     * 根据预算日记账头ID查询金额小于0或数量小于0的记录
     *
     * @param journalHeaderId 预算日记账头ID
     * @author guiyuting 2019-04-03 10:50
     * @return 
     */
    int queryNeedCheckCount(@Param("journalHeaderId") Long journalHeaderId);

    /**
     * 根据预算日记账头ID查询金额小于0或数量小于0的维度信息
     *
     * @param journalHeaderId 预算日记账头ID
     * @author guiyuting 2019-04-03 14:22
     * @return 
     */
    List<BgtJournalLine> queryDimByHeaderId(@Param("journalHeaderId") Long journalHeaderId);

    /**
     * 根据条件查询金额总数和数量总数
     *
     * @param bgtJournalLine
     * @author guiyuting 2019-04-03 15:32
     * @return 
     */
    Map<String,BigDecimal> querySumAmount(BgtJournalLine bgtJournalLine);

    /**
     * 根据条件查询预算余额金额总数和数量总数
     *
     * @param bgtJournalLine
     * @author guiyuting 2019-04-03 15:32
     * @return
     */
    Map<String,BigDecimal> queryBalanceSumAmount(BgtJournalLine bgtJournalLine);

    /**
     * 根据预算日记账头ID查询functionamout总和
     *
     * @param journalHeaderId 预算日记账头ID
     * @author guiyuting 2019-04-09 15:15
     * @return functionamout总和
     */
    BigDecimal queryTotalFunctionAmount(@Param("journalHeaderId") Long journalHeaderId);

}