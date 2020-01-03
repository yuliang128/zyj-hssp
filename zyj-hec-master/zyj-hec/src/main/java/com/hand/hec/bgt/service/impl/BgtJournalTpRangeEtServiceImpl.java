package com.hand.hec.bgt.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.hand.hec.bgt.dto.BgtJournalTpRangeEt;
import com.hand.hec.bgt.service.IBgtJournalTpRangeEtService;
import org.springframework.transaction.annotation.Transactional;
/**
 * <p>
 * 预算日记账类型分配预算实体范围ServiceImpl
 * </p>
 * 
 * @author mouse 2019/01/07 16:51
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class BgtJournalTpRangeEtServiceImpl extends BaseServiceImpl<BgtJournalTpRangeEt> implements IBgtJournalTpRangeEtService{

}