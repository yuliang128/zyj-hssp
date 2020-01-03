package com.hand.hec.bpm.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.bpm.dto.TpltLayoutTab;
import com.hand.hec.bpm.service.ITpltLayoutTabService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class TpltLayoutTabServiceImpl extends BaseServiceImpl<TpltLayoutTab> implements ITpltLayoutTabService {

}