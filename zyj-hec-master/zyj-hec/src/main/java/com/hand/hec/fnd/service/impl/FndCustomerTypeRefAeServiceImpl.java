package com.hand.hec.fnd.service.impl;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.fnd.mapper.FndCustomerTypeRefAeMapper;
import com.hand.hec.gld.dto.GldAccountingEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hand.hec.fnd.dto.FndCustomerTypeRefAe;
import com.hand.hec.fnd.service.IFndCustomerTypeRefAeService;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 客户类型关联核算主体ServiceImpl
 * </p>
 *
 * @author xiuxian.wu 2019/02/14 11:07
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class FndCustomerTypeRefAeServiceImpl extends BaseServiceImpl<FndCustomerTypeRefAe> implements IFndCustomerTypeRefAeService {
    @Autowired
    FndCustomerTypeRefAeMapper fndCustomerTypeRefAeMapper;
    @Autowired
    IFndCustomerTypeRefAeService service;

    @Override
    public List<GldAccountingEntity> queryAllAccountingEntity(FndCustomerTypeRefAe dto, IRequest request, Integer page, Integer pageSize) {
        PageHelper.startPage(page,pageSize );
        return fndCustomerTypeRefAeMapper.queryAllAccountingEntity(dto);
    }

    @Override
    public List<FndCustomerTypeRefAe> insertAccEntities(IRequest request, List<FndCustomerTypeRefAe> list) {
        List<FndCustomerTypeRefAe> data = new ArrayList<>();
        for (FndCustomerTypeRefAe t : list) {
            if (fndCustomerTypeRefAeMapper.queryFndCustomerTypeRefAe(t) == 0) {
                data.add(t);
            }
        }
        return service.batchUpdate(request, data);
    }
}