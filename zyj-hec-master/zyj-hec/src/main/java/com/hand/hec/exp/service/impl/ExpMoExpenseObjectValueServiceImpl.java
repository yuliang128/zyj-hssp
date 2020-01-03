package com.hand.hec.exp.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.hand.hec.exp.dto.ExpMoExpenseObjectValue;
import com.hand.hec.exp.service.IExpMoExpenseObjectValueService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class ExpMoExpenseObjectValueServiceImpl extends BaseServiceImpl<ExpMoExpenseObjectValue> implements IExpMoExpenseObjectValueService{

}