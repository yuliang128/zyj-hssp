package com.hand.hec.bgt.service.impl;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.bgt.mapper.BgtJournalTypeRefObjMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hand.hec.bgt.dto.BgtJournalTypeRefObj;
import com.hand.hec.bgt.service.IBgtJournalTypeRefObjService;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.ws.Action;
import java.util.List;

/**
 * <p>
 * 预算日记账类型关联费用对象ServiceImpl
 * </p>
 * 
 * @author mouse 2019/01/07 16:52
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class BgtJournalTypeRefObjServiceImpl extends BaseServiceImpl<BgtJournalTypeRefObj>
                implements IBgtJournalTypeRefObjService {

    @Autowired
    private BgtJournalTypeRefObjMapper journalTypeRefObjMapper;

    @Override
    public List<BgtJournalTypeRefObj> queryByBgtJournalType(IRequest request, BgtJournalTypeRefObj bgtJournalTypeRefObj,
                    int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        return journalTypeRefObjMapper.queryByBgtJournalType(bgtJournalTypeRefObj);
    }
}
