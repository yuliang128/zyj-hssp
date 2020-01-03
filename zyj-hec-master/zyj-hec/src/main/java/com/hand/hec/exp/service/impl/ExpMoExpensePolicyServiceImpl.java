package com.hand.hec.exp.service.impl;

import com.hand.hap.extensible.components.ServiceListenerChainFactory;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.exp.dto.ExpMoExpPolicyAsgnCom;
import com.hand.hec.exp.dto.ExpMoExpPolicyDetail;
import com.hand.hec.exp.dto.ExpMoExpensePolicy;
import com.hand.hec.exp.mapper.ExpMoExpPolicyAsgnComMapper;
import com.hand.hec.exp.mapper.ExpMoExpPolicyDetailMapper;
import com.hand.hec.exp.mapper.ExpMoExpensePolicyMapper;
import com.hand.hec.exp.service.IExpMoExpPolicyAsgnComService;
import com.hand.hec.exp.service.IExpMoExpPolicyDetailService;
import com.hand.hec.exp.service.IExpMoExpensePolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 政策标准定义ServiceImpl
 * </p>
 *
 * @author xiuxian.wu 2019/01/19 25:25
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ExpMoExpensePolicyServiceImpl extends BaseServiceImpl<ExpMoExpensePolicy>
                implements IExpMoExpensePolicyService {

    @Override
    protected boolean useSelectiveUpdate() {
        return false;
    }

    @Autowired
    private ServiceListenerChainFactory chainFactory;

    @Autowired
    ExpMoExpensePolicyMapper expMoExpensePolicyMapper;

    @Autowired
    ExpMoExpPolicyDetailMapper expMoExpPolicyDetailMapper;


    @Autowired
    IExpMoExpPolicyDetailService expMoExpPolicyDetailService;

    @Autowired
    ExpMoExpPolicyAsgnComMapper expMoExpPolicyAsgnComMapper;
    @Autowired
    IExpMoExpPolicyAsgnComService expMoExpPolicyAsgnComService;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteByPrimaryKey(ExpMoExpensePolicy record) {
        record = (ExpMoExpensePolicy) chainFactory.getChain(this).beforeDelete(null, record);
        ExpMoExpPolicyDetail data = new ExpMoExpPolicyDetail();
        data.setExpensePolicyId(record.getExpensePolicyId());
        List<ExpMoExpPolicyDetail> datas = expMoExpPolicyDetailMapper.select(data);
        for (ExpMoExpPolicyDetail d : datas) {
            expMoExpPolicyDetailService.deleteByPrimaryKey(d);
        }
        ExpMoExpPolicyAsgnCom data2 = new ExpMoExpPolicyAsgnCom();
        data2.setExpensePolicyId(record.getExpensePolicyId());
        List<ExpMoExpPolicyAsgnCom> datas2 = expMoExpPolicyAsgnComMapper.select(data2);
        for (ExpMoExpPolicyAsgnCom d2 : datas2) {
            expMoExpPolicyAsgnComService.deleteByPrimaryKey(d2);
        }
        int ret = mapper.deleteByPrimaryKey(record);
        checkOvn(ret, record);
        record = (ExpMoExpensePolicy) chainFactory.getChain(this).afterDelete(null, record);
        return ret;
    }
}
