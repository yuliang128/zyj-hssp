package com.hand.hec.fnd.service.impl;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.CodeValue;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.fnd.mapper.FndDimensionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hand.hec.fnd.dto.FndDimension;
import com.hand.hec.fnd.service.IFndDimensionService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 维度定义ServiceImpl
 *
 * @author xiuxian.wu 2019/02/18 20:09
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class FndDimensionServiceImpl extends BaseServiceImpl<FndDimension> implements IFndDimensionService {
    @Autowired
    FndDimensionMapper fndDimensionMapper;

    @Override
    public List<CodeValue> queryCodeValue(CodeValue dto, IRequest requestCtx) {
        return fndDimensionMapper.queryCodeValue(dto);
    }

    @Override
    public List<FndDimension> queryForDimensionValue(IRequest request, FndDimension dimension) {
        return fndDimensionMapper.queryForDimensionValue(dimension);
    }

    @Override
    public List<FndDimension> queryForCompanyDimensionValue(IRequest request, FndDimension dimension) {
        return fndDimensionMapper.queryForCompanyDimensionValue(dimension);
    }

    @Override
    public int checkDimExists(IRequest request, Long dimensionValueId) {
        return fndDimensionMapper.checkDimExists(dimensionValueId);
    }

    @Override
    public List<FndDimension> queryAll(IRequest request, FndDimension dimension, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        return fndDimensionMapper.queryAll(dimension);
    }
}
