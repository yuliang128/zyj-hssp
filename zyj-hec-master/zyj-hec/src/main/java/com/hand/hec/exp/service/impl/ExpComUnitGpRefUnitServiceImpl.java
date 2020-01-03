package com.hand.hec.exp.service.impl;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.Comparison;
import com.hand.hap.mybatis.common.query.WhereField;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.exp.mapper.ExpComUnitGpRefUnitMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hand.hec.exp.dto.ExpComUnitGpRefUnit;
import com.hand.hec.exp.service.IExpComUnitGpRefUnitService;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class ExpComUnitGpRefUnitServiceImpl extends BaseServiceImpl<ExpComUnitGpRefUnit> implements IExpComUnitGpRefUnitService{
    @Autowired
    private ExpComUnitGpRefUnitMapper mapper;

    @Override
    public List<ExpComUnitGpRefUnit> batchAssignCompany(IRequest request, List<ExpComUnitGpRefUnit> unitList) {
        List<ExpComUnitGpRefUnit> list = new ArrayList<>();
        for (ExpComUnitGpRefUnit expComUnitGpRefUnit : unitList) {
            int count = mapper.selectCount(expComUnitGpRefUnit);
            if (count == 1) {
                Criteria criteria = new Criteria(expComUnitGpRefUnit);
                criteria.where(new WhereField(ExpComUnitGpRefUnit.FIELD_UNIT_GROUP_COM_ASSIGN_ID, Comparison.EQUAL),
                                new WhereField(ExpComUnitGpRefUnit.FIELD_UNIT_ID, Comparison.EQUAL));
                List<ExpComUnitGpRefUnit> expComUnitGpRefUnitList = mapper.selectOptions(expComUnitGpRefUnit, criteria);
                if (!expComUnitGpRefUnitList.isEmpty()) {
                    ExpComUnitGpRefUnit preGpRefUnit = expComUnitGpRefUnitList.get(0);
                    expComUnitGpRefUnit.setUnitId(preGpRefUnit.getUnitId());
                    expComUnitGpRefUnit = self().updateByPrimaryKey(request, expComUnitGpRefUnit);
                }
            } else {
                expComUnitGpRefUnit = self().insertSelective(request, expComUnitGpRefUnit);
            }
            list.add(expComUnitGpRefUnit);
        }
        return list;

    }

    @Override
    public List<ExpComUnitGpRefUnit> queryRefUnitInfo(IRequest request, ExpComUnitGpRefUnit expComUnitGpRefUnit,
                    int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        return mapper.queryRefUnitInfo(expComUnitGpRefUnit);
    }
}
