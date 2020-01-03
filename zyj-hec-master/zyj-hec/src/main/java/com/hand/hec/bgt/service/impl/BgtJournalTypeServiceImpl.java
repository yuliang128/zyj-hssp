package com.hand.hec.bgt.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.WhereField;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.bgt.mapper.BgtJournalTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hand.hec.bgt.dto.BgtJournalType;
import com.hand.hec.bgt.service.IBgtJournalTypeService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 预算日记账类型ServiceImpl
 * </p>
 * 
 * @author mouse 2019/01/07 16:52
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class BgtJournalTypeServiceImpl extends BaseServiceImpl<BgtJournalType> implements IBgtJournalTypeService {

    @Autowired
    BgtJournalTypeMapper mapper;

    @Override
    public BgtJournalType getBgtJournalType(IRequest request, Long journalTypeId) {
        BgtJournalType type = new BgtJournalType();
        type.setBgtJournalTypeId(journalTypeId);
        Criteria criteria = new Criteria(type);
        criteria.where(new WhereField(BgtJournalType.FIELD_BGT_JOURNAL_TYPE_ID));
        List<BgtJournalType> typeList = selectOptions(request, type, criteria);
        if (typeList != null && typeList.size() != 0) {
            return typeList.get(0);
        } else {
            return null;
        }
    }

    @Override
    public List<Map> getJournalTypeObjInfo(IRequest request, Long bgtJournalTypeId, String position) {
        return mapper.getJournalTypeObjInfo(bgtJournalTypeId, position);
    }

    @Override
    public BgtJournalType queryInfoByBgtJournal(Long journalHeaderId) {
        return mapper.queryInfoByBgtJournal(journalHeaderId);
    }
}
