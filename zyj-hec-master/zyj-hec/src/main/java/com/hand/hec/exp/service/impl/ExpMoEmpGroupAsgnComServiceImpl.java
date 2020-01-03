package com.hand.hec.exp.service.impl;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.exp.mapper.ExpMoEmpGroupAsgnComMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hand.hec.exp.dto.ExpMoEmpGroupAsgnCom;
import com.hand.hec.exp.service.IExpMoEmpGroupAsgnComService;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 员工组关联公司ServiceImpl
 * </p>
 *
 * @author xiuxian.wu 2019/03/06 14:48
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ExpMoEmpGroupAsgnComServiceImpl extends BaseServiceImpl<ExpMoEmpGroupAsgnCom> implements IExpMoEmpGroupAsgnComService {
    @Autowired
    IExpMoEmpGroupAsgnComService expMoEmpGroupAsgnComService;
    @Autowired
    ExpMoEmpGroupAsgnComMapper expMoEmpGroupAsgnComMapper;

    @Override
    public List<ExpMoEmpGroupAsgnCom> queryAllCompanyInformation(IRequest request, ExpMoEmpGroupAsgnCom dto, Integer page, Integer PageSize) {
        PageHelper.startPage(page, PageSize);
        return expMoEmpGroupAsgnComMapper.queryAllCompanyInformation(dto);
    }

    @Override
    public List<ExpMoEmpGroupAsgnCom> insertCompany(IRequest request, List<ExpMoEmpGroupAsgnCom> list) {
        List<ExpMoEmpGroupAsgnCom> data = new ArrayList<>();
        for (ExpMoEmpGroupAsgnCom t : list) {
            if (expMoEmpGroupAsgnComMapper.queryMoEmpGroupAsgnCom(t) == 0) {
                data.add(t);
            }
        }
        return expMoEmpGroupAsgnComService.batchUpdate(request, data);
    }
}