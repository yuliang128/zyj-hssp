package com.hand.hec.exp.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.hand.hec.exp.dto.ExpLevelSeries;
import com.hand.hec.exp.service.IExpLevelSeriesService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class ExpLevelSeriesServiceImpl extends BaseServiceImpl<ExpLevelSeries> implements IExpLevelSeriesService{

}