package com.hand.hec.bgt.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.core.annotation.StdWho;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.bgt.dto.BgtJournalHeader;

import java.util.List;

/**
 * <p>
 * 预算日记账头Service
 * </p>
 *
 * @author mouse 2019/01/07 16:56
 */
public interface IBgtJournalHeaderService extends IBaseService<BgtJournalHeader>, ProxySelf<IBgtJournalHeaderService> {

    /**
     * 我的预算日记账-查询列表
     *
     * @param request
     * @param dto
     * @param page
     * @param pagesize
     * @author mouse 2019-03-14 15:47
     * @return java.util.List<com.hand.hec.bgt.dto.BgtJournalHeader>
     */
    List<BgtJournalHeader> queryMain(IRequest request, BgtJournalHeader dto, int page, int pagesize);

    /**
     * 预算日记账维护-查询预算日记账头
     *
     * @param request
     * @param dto
     * @author mouse 2019-03-14 15:47
     * @return com.hand.hec.bgt.dto.BgtJournalHeader
     */
    BgtJournalHeader queryJournalHeader(IRequest request, BgtJournalHeader dto);

    /**
     * 保存预算日记账头信息
     *
     * @param request
     * @param header
     * @author mouse 2019-03-26 14:34
     * @return void
     */
    BgtJournalHeader saveJournalHeader(IRequest request, @StdWho BgtJournalHeader header);



    /**
     * 设置预算日记账状态
     *
     * @param list
     * @author guiyuting 2019-03-27 17:23
     * @return
     */
    List<BgtJournalHeader> setBgtJournalStatus(IRequest request, List<BgtJournalHeader> list, String status);

    /**
     * 预算日记账审批页面，查询已审批或已提交的未触发工作流的数据
     *
     * @param header
     * @author guiyuting 2019-04-10 14:57
     * @return
     */
    List<BgtJournalHeader> queryJournalForApprove(IRequest request, BgtJournalHeader header, int page, int pageSize);
}
