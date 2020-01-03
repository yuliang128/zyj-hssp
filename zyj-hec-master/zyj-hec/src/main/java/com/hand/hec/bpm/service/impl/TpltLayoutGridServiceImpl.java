package com.hand.hec.bpm.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.bpm.dto.TpltLayoutGrid;
import com.hand.hec.bpm.service.ITpltLayoutGridService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class TpltLayoutGridServiceImpl extends BaseServiceImpl<TpltLayoutGrid> implements ITpltLayoutGridService {

}