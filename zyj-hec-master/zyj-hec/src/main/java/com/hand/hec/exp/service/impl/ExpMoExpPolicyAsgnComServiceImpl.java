package com.hand.hec.exp.service.impl;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.exp.mapper.ExpMoExpPolicyAsgnComMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hand.hec.exp.dto.ExpMoExpPolicyAsgnCom;
import com.hand.hec.exp.service.IExpMoExpPolicyAsgnComService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 政策标准关联管理公司ServiceImpl
 * </p>
 *
 * @author xiuxian.wu 2019/01/19 25:25
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ExpMoExpPolicyAsgnComServiceImpl extends BaseServiceImpl<ExpMoExpPolicyAsgnCom> implements IExpMoExpPolicyAsgnComService {

    @Autowired
    ExpMoExpPolicyAsgnComMapper expMoExpPolicyAsgnComMapper;

    @Override
    public List<ExpMoExpPolicyAsgnCom> queryCompanyByExpensePolicyId(IRequest request, ExpMoExpPolicyAsgnCom condition, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return expMoExpPolicyAsgnComMapper.queryCompanyByExpensePolicyId(condition);
    }

    @Override
    public List<ExpMoExpPolicyAsgnCom> queryRemainingCompanyByExpensePolicyId(IRequest request, ExpMoExpPolicyAsgnCom condition, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return expMoExpPolicyAsgnComMapper.queryRemainingCompanyByExpensePolicyId(condition);
    }
}