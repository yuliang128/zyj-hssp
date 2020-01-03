package com.hand.hec.bpm.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.bpm.mapper.TpltLayoutFormMapper;
import com.hand.hec.bpm.dto.TpltLayoutForm;
import com.hand.hec.bpm.service.ITpltLayoutFormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uncertain.composite.CompositeMap;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@Transactional(rollbackFor = Exception.class)
public class TpltLayoutFormServiceImpl extends BaseServiceImpl<TpltLayoutForm> implements ITpltLayoutFormService {
    @Autowired
    TpltLayoutFormMapper bpmTpltLayoutFormMapper;

}