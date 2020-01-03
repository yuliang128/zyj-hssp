package com.hand.hec.cont.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.hand.hec.cont.dto.ConContractHeader;
import com.hand.hec.cont.service.IConContractHeaderService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class ConContractHeaderServiceImpl extends BaseServiceImpl<ConContractHeader> implements IConContractHeaderService{

}