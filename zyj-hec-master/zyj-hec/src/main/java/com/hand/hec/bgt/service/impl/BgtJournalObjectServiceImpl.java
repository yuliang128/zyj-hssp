package com.hand.hec.bgt.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.hand.hec.bgt.dto.BgtJournalObject;
import com.hand.hec.bgt.service.IBgtJournalObjectService;
import org.springframework.transaction.annotation.Transactional;
/**
 * <p>
 * 预算日记账关联费用对象ServiceImpl
 * </p>
 * 
 * @author mouse 2019/01/07 16:51
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class BgtJournalObjectServiceImpl extends BaseServiceImpl<BgtJournalObject> implements IBgtJournalObjectService{

}