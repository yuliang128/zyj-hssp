package com.hand.hec.exp.service.impl;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.exp.mapper.ExpComEmpGroupRefEmpMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hand.hec.exp.dto.ExpComEmpGroupRefEmp;
import com.hand.hec.exp.service.IExpComEmpGroupRefEmpService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 员工分配员工组ServiceImpl
 * </p>
 *
 * @author xiuxian.wu 2019/01/25 14:48
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ExpComEmpGroupRefEmpServiceImpl extends BaseServiceImpl<ExpComEmpGroupRefEmp> implements IExpComEmpGroupRefEmpService {
    @Autowired
    ExpComEmpGroupRefEmpMapper expComEmpGroupRefEmpMapper;
    @Override
    protected boolean useSelectiveUpdate(){
        return false;
    }
    @Override
    public List<ExpComEmpGroupRefEmp> queryAll(IRequest requestContext, ExpComEmpGroupRefEmp dto, Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        return expComEmpGroupRefEmpMapper.queryAll(dto);
    }
}