package com.hand.hec.exp.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.hand.hec.exp.dto.ExpMoExpTypeRefReqIt;
import com.hand.hec.exp.service.IExpMoExpTypeRefReqItService;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 报销类型关联申请项目ServiceImpl
 * </p>
 *
 * @author xiuxian.wu 2019/01/27 14:24
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ExpMoExpTypeRefReqItServiceImpl extends BaseServiceImpl<ExpMoExpTypeRefReqIt> implements IExpMoExpTypeRefReqItService {

}