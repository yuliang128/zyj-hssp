package com.hand.hec.bpm.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.bpm.mapper.TpltTagBasicMapper;
import com.hand.hec.bpm.dto.TpltTagBasic;
import com.hand.hec.bpm.service.ITpltTagBasicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uncertain.composite.CompositeMap;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class TpltTagBasicServiceImpl extends BaseServiceImpl<TpltTagBasic> implements ITpltTagBasicService {
    @Autowired
    TpltTagBasicMapper bpmTpltTagsBasicMapper;

    @Override
    public List<TpltTagBasic> queryByLayoutId(IRequest request, TpltTagBasic bpmTpltTagsBasic) {
        return bpmTpltTagsBasicMapper.queryByLayoutId(bpmTpltTagsBasic);
    }

    @Override
    public List<TpltTagBasic> selectTpltTagBasicLov(IRequest request, TpltTagBasic bpmTpltTagsBasic) {
        return bpmTpltTagsBasicMapper.selectTpltTagBasicLov(bpmTpltTagsBasic);
    }


}