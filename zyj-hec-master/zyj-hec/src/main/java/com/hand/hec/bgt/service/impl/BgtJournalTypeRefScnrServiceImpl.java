package com.hand.hec.bgt.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.hand.hec.bgt.dto.BgtJournalTypeRefScnr;
import com.hand.hec.bgt.service.IBgtJournalTypeRefScnrService;
import org.springframework.transaction.annotation.Transactional;
/**
 * <p>
 * 预算日记账类型关联场景ServiceImpl
 * </p>
 * 
 * @author mouse 2019/01/07 16:52
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class BgtJournalTypeRefScnrServiceImpl extends BaseServiceImpl<BgtJournalTypeRefScnr> implements IBgtJournalTypeRefScnrService{

}