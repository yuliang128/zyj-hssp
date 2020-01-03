package com.hand.hec.exp.service.impl;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.DTOStatus;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.exp.mapper.ExpMoExpPolicyCondHdsMapper;
import com.hand.hec.exp.mapper.ExpMoExpPolicyCondLnsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hand.hec.exp.dto.ExpMoExpPolicyCondLns;
import com.hand.hec.exp.service.IExpMoExpPolicyCondLnsService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 政策标准明细条件行ServiceImpl
 * </p>
 *
 * @author xiuxian.wu 2019-02-21
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ExpMoExpPolicyCondLnsServiceImpl extends BaseServiceImpl<ExpMoExpPolicyCondLns> implements IExpMoExpPolicyCondLnsService {
    @Autowired
    ExpMoExpPolicyCondLnsMapper expMoExpPolicyCondLnsMapper;
    @Autowired
    IExpMoExpPolicyCondLnsService expMoExpPolicyCondLnsService;
    @Autowired
    ExpMoExpPolicyCondHdsMapper expMoExpPolicyCondHdsMapper;
    @Override
    protected boolean useSelectiveUpdate(){
        return false;
    }
    @Override
    public List<ExpMoExpPolicyCondLns> queryPolicyCondLns(IRequest request, ExpMoExpPolicyCondLns condition, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return expMoExpPolicyCondLnsMapper.queryPolicyCondLns(condition);
    }

    @Override
    public List<ExpMoExpPolicyCondLns> batchSubmit(IRequest request, List<ExpMoExpPolicyCondLns> conditions) {
        for (ExpMoExpPolicyCondLns condition : conditions) {
            if (DTOStatus.ADD.equals(condition.get__status())) {
                condition.setConditionHdsId(expMoExpPolicyCondHdsMapper.queryConditionHdsId(condition));
            }
        }
        return expMoExpPolicyCondLnsService.batchUpdate(request, conditions);
    }
}