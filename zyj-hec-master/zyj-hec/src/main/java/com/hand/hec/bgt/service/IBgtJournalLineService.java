package com.hand.hec.bgt.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.core.annotation.StdWho;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.bgt.dto.BgtJournalLine;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 预算日记账行Service
 * </p>
 * 
 * @author mouse 2019/01/07 17:00
 */
public interface IBgtJournalLineService extends IBaseService<BgtJournalLine>, ProxySelf<IBgtJournalLineService> {

    /**
     * 保存预算日记账行信息
     *
     * @param requestCtx
     * @param line
     * @author mouse 2019-03-26 14:34
     * @return void
     */
    void saveJournalLine(IRequest requestCtx, @StdWho BgtJournalLine line);

    /**
     * 根据预算日记账头ID获取所有行信息
     *
     * @param journalHeaderId 预算日记账头ID
     * @author guiyuting 2019-03-28 15:07
     * @return 预算日记账行信息
     */
    List<BgtJournalLine> queryInfoByHeaderId(IRequest request, Long journalHeaderId);

    /**
     * 根据预算日记账头ID查询金额小于0或数量小于0的记录
     *
     * @param journalHeaderId 预算日记账头ID
     * @author guiyuting 2019-04-03 10:50
     * @return
     */
    int queryNeedCheckCount(Long journalHeaderId);

    /**
     * 根据预算日记账头ID查询金额小于0或数量小于0的维度信息
     *
     * @param journalHeaderId 预算日记账头ID
     * @author guiyuting 2019-04-03 14:22
     * @return
     */
    List<BgtJournalLine> queryDimByHeaderId(Long journalHeaderId);

    /**
     * 根据条件查询金额总数和数量总数
     *
     * @param bgtJournalLine
     * @author guiyuting 2019-04-03 15:32
     * @return
     */
    Map<String, BigDecimal> querySumAmount(BgtJournalLine bgtJournalLine);

    /**
     * 根据条件查询预算余额金额总数和数量总数
     *
     * @param bgtJournalLine
     * @author guiyuting 2019-04-03 15:32
     * @return
     */
    Map<String, BigDecimal> queryBalanceSumAmount(BgtJournalLine bgtJournalLine);

    /**
     * 根据预算日记账头ID查询functionamout总和
     *
     * @param journalHeaderId 预算日记账头ID
     * @author guiyuting 2019-04-09 15:15
     * @return functionamout总和
     */
    BigDecimal queryTotalFunctionAmount(Long journalHeaderId);


}
