package com.hand.hec.exp.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.exp.mapper.ExpMoReqItemDescMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hand.hec.exp.dto.ExpMoReqItemDesc;
import com.hand.hec.exp.service.IExpMoReqItemDescService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
/**
 * <p>
 * 申请项目说明Service实现类
 * </p>
 *
 * @author yang.cai 2019/02/27 18:43
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ExpMoReqItemDescServiceImpl extends BaseServiceImpl<ExpMoReqItemDesc> implements IExpMoReqItemDescService{

    @Autowired
    ExpMoReqItemDescMapper mapper;
    @Override
    public List<ExpMoReqItemDesc> queryAll(IRequest request, ExpMoReqItemDesc expMoReqItemDesc, int pageNum, int pageSize) {
        return mapper.queryAll(expMoReqItemDesc);
    }
}