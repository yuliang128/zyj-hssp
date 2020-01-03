package com.hand.hec.exp.service.impl;

import com.hand.hap.extensible.components.ServiceListenerChainFactory;
import com.hand.hap.system.dto.CodeValue;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.exp.dto.ExpMoExpPolicyCondLns;
import com.hand.hec.exp.mapper.ExpMoExpPolicyCondHdsMapper;
import com.hand.hec.exp.mapper.ExpMoExpPolicyCondLnsMapper;
import com.hand.hec.exp.mapper.ExpMoExpPolicyConditionMapper;
import com.hand.hec.exp.service.IExpMoExpPolicyCondLnsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hand.hec.exp.dto.ExpMoExpPolicyCondHds;
import com.hand.hec.exp.service.IExpMoExpPolicyCondHdsService;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 政策标准明细条件头ServiceImpl
 * </p>
 *
 * @author xiuxian.wu 2019-02-21
 */

@Service
@Transactional(rollbackFor = Exception.class)
public class ExpMoExpPolicyCondHdsServiceImpl extends BaseServiceImpl<ExpMoExpPolicyCondHds> implements IExpMoExpPolicyCondHdsService {
    @Autowired
    ExpMoExpPolicyCondHdsMapper expMoExpPolicyCondHdsMapper;
    @Autowired
    ExpMoExpPolicyConditionMapper expMoExpPolicyConditionMapper;
    @Autowired
    private ServiceListenerChainFactory chainFactory;
    @Autowired
    ExpMoExpPolicyCondLnsMapper expMoExpPolicyCondLnsMapper;
    @Autowired
    IExpMoExpPolicyCondLnsService expMoExpPolicyCondLnsService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteByPrimaryKey(ExpMoExpPolicyCondHds record) {
        record = (ExpMoExpPolicyCondHds) chainFactory.getChain(this).beforeDelete(null, record);
        ExpMoExpPolicyCondLns data = new ExpMoExpPolicyCondLns();
        data.setConditionHdsId(record.getConditionHdsId());
        List<ExpMoExpPolicyCondLns> datas = expMoExpPolicyCondLnsMapper.select(data);
        for (ExpMoExpPolicyCondLns d : datas) {
            expMoExpPolicyCondLnsService.deleteByPrimaryKey(d);
        }
        int ret = mapper.deleteByPrimaryKey(record);
        checkOvn(ret, record);
        record = (ExpMoExpPolicyCondHds) chainFactory.getChain(this).afterDelete(null, record);
        return ret;
    }

    @Override
    protected boolean useSelectiveUpdate() {
        return false;
    }

    @Override
    public Map<String, String> queryAllMatchItemCode(Long conditionId) {
        Map<String, String> dataFlag = new HashMap<>();
        List<String> itemCodes = expMoExpPolicyCondHdsMapper.queryAllMatchItemCode(conditionId);
        List<CodeValue> conditions = expMoExpPolicyConditionMapper.queryAllMatchingCondition();
        for (CodeValue value : conditions) {
            dataFlag.put(value.getValue(), "none");
        }
        for (String s : itemCodes) {
            dataFlag.put(s, "inline");
        }
        return dataFlag;
    }
}