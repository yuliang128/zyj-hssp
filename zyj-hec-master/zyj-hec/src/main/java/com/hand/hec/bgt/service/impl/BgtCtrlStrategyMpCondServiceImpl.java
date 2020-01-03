package com.hand.hec.bgt.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.hand.hec.bgt.dto.BgtCtrlStrategyMpCond;
import com.hand.hec.bgt.service.IBgtCtrlStrategyMpCondService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class BgtCtrlStrategyMpCondServiceImpl extends BaseServiceImpl<BgtCtrlStrategyMpCond> implements IBgtCtrlStrategyMpCondService{

}