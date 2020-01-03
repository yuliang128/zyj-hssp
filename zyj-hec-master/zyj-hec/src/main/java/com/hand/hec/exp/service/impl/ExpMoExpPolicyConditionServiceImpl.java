package com.hand.hec.exp.service.impl;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.extensible.components.ServiceListenerChainFactory;
import com.hand.hap.system.dto.CodeValue;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.exp.dto.ExpMoExpPolicyCondHds;
import com.hand.hec.exp.mapper.ExpMoExpPolicyCondHdsMapper;
import com.hand.hec.exp.mapper.ExpMoExpPolicyConditionMapper;
import com.hand.hec.exp.service.IExpMoExpPolicyCondHdsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hand.hec.exp.dto.ExpMoExpPolicyCondition;
import com.hand.hec.exp.service.IExpMoExpPolicyConditionService;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 政策标准明细条件ServiceImpl
 * </p>
 *
 * @author xiuxian.wu 2019/01/21 10:20
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ExpMoExpPolicyConditionServiceImpl extends BaseServiceImpl<ExpMoExpPolicyCondition> implements IExpMoExpPolicyConditionService {
    @Autowired
    ExpMoExpPolicyConditionMapper expMoExpPolicyConditionMapper;

    @Autowired
    IExpMoExpPolicyConditionService iExpMoExpPolicyConditionService;

    @Autowired
    IExpMoExpPolicyCondHdsService iExpMoExpPolicyCondHdsService;

    @Autowired
    private ServiceListenerChainFactory chainFactory;
    @Autowired
    ExpMoExpPolicyCondHdsMapper expMoExpPolicyCondHdsMapper;
    @Autowired
    IExpMoExpPolicyCondHdsService expMoExpPolicyCondHdsService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteByPrimaryKey(ExpMoExpPolicyCondition record) {
        record = (ExpMoExpPolicyCondition) chainFactory.getChain(this).beforeDelete(null, record);
        ExpMoExpPolicyCondHds data = new ExpMoExpPolicyCondHds();
        data.setConditionId(record.getConditionId());
        List<ExpMoExpPolicyCondHds> datas = expMoExpPolicyCondHdsMapper.select(data);
        for (ExpMoExpPolicyCondHds d : datas) {
            expMoExpPolicyCondHdsService.deleteByPrimaryKey(d);
        }
        int ret = mapper.deleteByPrimaryKey(record);
        checkOvn(ret, record);
        record = (ExpMoExpPolicyCondition) chainFactory.getChain(this).afterDelete(null, record);
        return ret;
    }

    @Override
    protected boolean useSelectiveUpdate() {
        return false;
    }

    @Override
    public List<CodeValue> queryAllMatchingCondition(IRequest requestContext, Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        return expMoExpPolicyConditionMapper.queryAllMatchingCondition();
    }

    @Override
    public List<ExpMoExpPolicyCondition> submitHds(IRequest request, List<ExpMoExpPolicyCondition> list) {
        ExpMoExpPolicyCondition con = iExpMoExpPolicyConditionService.insert(request, list.get(0));
        List<ExpMoExpPolicyCondHds> condHds = new ArrayList<>();
        Long conditionId = con.getConditionId();
        for (ExpMoExpPolicyCondition condition : list) {
            ExpMoExpPolicyCondHds hds = new ExpMoExpPolicyCondHds();
            hds.setConditionId(conditionId);
            hds.setMatchItemCode(condition.getValue());
            hds.set__status(con.get__status());
            hds.set__id(con.get__id());
            condHds.add(hds);
        }
        iExpMoExpPolicyCondHdsService.batchUpdate(request, condHds);
        return list;
    }

}