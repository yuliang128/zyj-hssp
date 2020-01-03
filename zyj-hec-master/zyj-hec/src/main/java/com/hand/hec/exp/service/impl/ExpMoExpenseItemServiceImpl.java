package com.hand.hec.exp.service.impl;

import com.hand.hap.mybatis.common.query.Where;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.hand.hec.exp.dto.ExpMoExpenseItem;
import com.hand.hec.exp.service.IExpMoExpenseItemService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class ExpMoExpenseItemServiceImpl extends BaseServiceImpl<ExpMoExpenseItem> implements IExpMoExpenseItemService{


    @Override
    protected boolean useSelectiveUpdate(){
        return false;
    }
}