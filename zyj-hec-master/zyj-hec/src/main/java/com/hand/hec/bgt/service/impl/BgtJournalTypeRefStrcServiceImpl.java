package com.hand.hec.bgt.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.hand.hec.bgt.dto.BgtJournalTypeRefStrc;
import com.hand.hec.bgt.service.IBgtJournalTypeRefStrcService;
import org.springframework.transaction.annotation.Transactional;
/**
 * <p>
 * 预算日记账类型关联预算表ServiceImpl
 * </p>
 * 
 * @author mouse 2019/01/07 16:52
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class BgtJournalTypeRefStrcServiceImpl extends BaseServiceImpl<BgtJournalTypeRefStrc> implements IBgtJournalTypeRefStrcService{

}