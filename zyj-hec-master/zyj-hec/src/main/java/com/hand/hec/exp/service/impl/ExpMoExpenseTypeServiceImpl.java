package com.hand.hec.exp.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.hand.hec.exp.dto.ExpMoExpenseType;
import com.hand.hec.exp.service.IExpMoExpenseTypeService;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 报销类型定义ServiceImpl
 * </p>
 *
 * @author xiuxian.wu 2019/01/27 14:24
 */

@Service
@Transactional(rollbackFor = Exception.class)
public class ExpMoExpenseTypeServiceImpl extends BaseServiceImpl<ExpMoExpenseType> implements IExpMoExpenseTypeService {

}