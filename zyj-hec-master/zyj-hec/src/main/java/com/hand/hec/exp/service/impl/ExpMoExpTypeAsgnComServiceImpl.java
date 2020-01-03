package com.hand.hec.exp.service.impl;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.exp.mapper.ExpMoExpTypeAsgnComMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hand.hec.exp.dto.ExpMoExpTypeAsgnCom;
import com.hand.hec.exp.service.IExpMoExpTypeAsgnComService;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 报销类型关联公司ServiceImpl
 * </p>
 *
 * @author xiuxian.wu 2019/01/27 14:24
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ExpMoExpTypeAsgnComServiceImpl extends BaseServiceImpl<ExpMoExpTypeAsgnCom> implements IExpMoExpTypeAsgnComService {
    @Autowired
    ExpMoExpTypeAsgnComMapper expMoExpTypeAsgnComMapper;
    @Autowired
    IExpMoExpTypeAsgnComService service;

    @Override
    public List<ExpMoExpTypeAsgnCom> queryRemainAllCompany(IRequest requestContext, ExpMoExpTypeAsgnCom dto, Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        return expMoExpTypeAsgnComMapper.queryRemainAllCompany(dto);
    }

    @Override
    public List<ExpMoExpTypeAsgnCom> insertCompany(IRequest request, List<ExpMoExpTypeAsgnCom> list) {
        List<ExpMoExpTypeAsgnCom> data = new ArrayList<>();
        for (ExpMoExpTypeAsgnCom t : list) {
            if (expMoExpTypeAsgnComMapper.queryExpTypeAsgnCompany(t) == 0) {
                data.add(t);
            }
        }
        return service.batchUpdate(request, data);
    }
}