package com.hand.hec.bgt.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.hand.hec.bgt.dto.BgtJournalTpRangeCen;
import com.hand.hec.bgt.service.IBgtJournalTpRangeCenService;
import org.springframework.transaction.annotation.Transactional;
/**
 * <p>
 * 预算日记账类型分配预算中心范围ServiceImpl
 * </p>
 * 
 * @author mouse 2019/01/07 16:51
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class BgtJournalTpRangeCenServiceImpl extends BaseServiceImpl<BgtJournalTpRangeCen> implements IBgtJournalTpRangeCenService{

}