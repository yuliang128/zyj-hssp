package com.hand.hec.exp.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.hand.hec.exp.dto.ExpMoExpTypeRefExpIt;
import com.hand.hec.exp.service.IExpMoExpTypeRefExpItService;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 报销类型关联费用项目ServiceImpl
 * </p>
 *
 * @author xiuxian.wu 2019/01/27 14:24
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ExpMoExpTypeRefExpItServiceImpl extends BaseServiceImpl<ExpMoExpTypeRefExpIt> implements IExpMoExpTypeRefExpItService {

}