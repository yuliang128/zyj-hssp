package com.hand.hec.bgt.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.bgt.dto.BgtJournalHeader;

import java.util.List;

/**
 * <p>
 * 预算日记账头Mapper
 * </p>
 * 
 * @author mouse 2019/01/07 16:48
 */
public interface BgtJournalHeaderMapper extends Mapper<BgtJournalHeader> {

    List<BgtJournalHeader> queryJournalMain(BgtJournalHeader header);

    /**
     * 预算日记账审批页面，查询已审批或已提交的未触发工作流的数据
     *
     * @param header
     * @author guiyuting 2019-04-10 14:57
     * @return 
     */
    List<BgtJournalHeader> queryJournalForApprove(BgtJournalHeader header);
}
