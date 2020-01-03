package com.hand.hec.bpm.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.DTOStatus;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.bpm.mapper.TpltButtonMapper;
import com.hand.hec.bpm.mapper.TpltLayoutButtonMapper;
import com.hand.hec.bpm.dto.TpltLayoutBasic;
import com.hand.hec.bpm.dto.TpltLayoutButton;
import com.hand.hec.bpm.service.ITpltLayoutBasicService;
import com.hand.hec.bpm.service.ITpltLayoutButtonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uncertain.composite.CompositeMap;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@Transactional(rollbackFor = Exception.class)
public class TpltLayoutButtonServiceImpl extends BaseServiceImpl<TpltLayoutButton> implements ITpltLayoutButtonService {
    @Autowired
    TpltLayoutButtonMapper bpmTpltLayoutButtonsMapper;
    @Autowired
    TpltButtonMapper bpmTpltButtonsMapper;

    @Autowired
    ITpltLayoutBasicService iBpmTpltLayoutBasicService;

}