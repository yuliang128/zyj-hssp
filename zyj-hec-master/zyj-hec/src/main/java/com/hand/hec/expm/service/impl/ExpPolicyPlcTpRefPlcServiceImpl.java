package com.hand.hec.expm.service.impl;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.expm.mapper.ExpPolicyPlcTpRefPlcMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hand.hec.expm.dto.ExpPolicyPlcTpRefPlc;
import com.hand.hec.expm.service.IExpPolicyPlcTpRefPlcService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 费用政策地点类型分配政策地点ServiceImpl
 * </p>
 *
 * @author xiuxian.wu 2019/01/27 15:50
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ExpPolicyPlcTpRefPlcServiceImpl extends BaseServiceImpl<ExpPolicyPlcTpRefPlc> implements IExpPolicyPlcTpRefPlcService {
    @Autowired
    ExpPolicyPlcTpRefPlcMapper expPolicyPlcTpRefPlcMapper;

    @Override
    public List<ExpPolicyPlcTpRefPlc> queryAll(IRequest requestContext, ExpPolicyPlcTpRefPlc dto, Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        return expPolicyPlcTpRefPlcMapper.queryAll(dto);
    }

    @Override
    public List<ExpPolicyPlcTpRefPlc> queryRemainPolicyPlace(IRequest requestContext, ExpPolicyPlcTpRefPlc dto, Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        return expPolicyPlcTpRefPlcMapper.queryRemainPolicyPlace(dto);
    }
}