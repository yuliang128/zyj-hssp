package com.hand.hec.bgt.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.hand.hec.bgt.dto.BgtJournalBalanceExtendBak;
import com.hand.hec.bgt.service.IBgtJournalBalanceExtendBakService;
import org.springframework.transaction.annotation.Transactional;
/**
 * <p>
 * 预算余额币种扩展备份ServiceImpl
 * </p>
 * 
 * @author mouse 2019/01/07 16:51
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class BgtJournalBalanceExtendBakServiceImpl extends BaseServiceImpl<BgtJournalBalanceExtendBak> implements IBgtJournalBalanceExtendBakService{

}