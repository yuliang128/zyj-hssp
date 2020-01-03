package com.hand.hec.fnd.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.fnd.mapper.FndCustomerTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hand.hec.fnd.dto.FndCustomerType;
import com.hand.hec.fnd.service.IFndCustomerTypeService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 客户类型定义ServiceImpl
 * </p>
 *
 * @author xiuxian.wu 2019/02/14 11:07
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class FndCustomerTypeServiceImpl extends BaseServiceImpl<FndCustomerType> implements IFndCustomerTypeService {
    @Autowired
    private FndCustomerTypeMapper customerTypeMapper;

    @Override
    public List<FndCustomerType> queryByAccEntity(IRequest request, Long accEntityId) {
        return customerTypeMapper.queryByAccEntity(accEntityId);
    }
}