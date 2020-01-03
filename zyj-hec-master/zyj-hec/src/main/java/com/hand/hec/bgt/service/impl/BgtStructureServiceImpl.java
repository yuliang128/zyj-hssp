package com.hand.hec.bgt.service.impl;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hand.hap.core.IRequest;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.WhereField;
import com.hand.hap.system.dto.DTOStatus;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.bgt.dto.BgtJournalHeader;
import com.hand.hec.bgt.dto.BgtStructure;
import com.hand.hec.bgt.dto.BgtStructureLayout;
import com.hand.hec.bgt.exception.BgtStructureException;
import com.hand.hec.bgt.mapper.BgtJournalHeaderMapper;
import com.hand.hec.bgt.mapper.BgtStructureMapper;
import com.hand.hec.bgt.service.IBgtStructureLayoutService;
import com.hand.hec.bgt.service.IBgtStructureService;
import com.hand.hec.fnd.dto.FndDimension;
import com.hand.hec.fnd.dto.FndDimensionValue;
import com.hand.hec.fnd.service.IFndDimensionService;
import com.hand.hec.fnd.service.IFndDimensionValueService;

/**
 * <p>
 * 预算表ServiceImpl
 * </p>
 *
 * @author mouse 2019/01/07 16:53
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class BgtStructureServiceImpl extends BaseServiceImpl<BgtStructure> implements IBgtStructureService {

    @Autowired
    private BgtStructureMapper mapper;

    @Autowired
    private BgtJournalHeaderMapper journalHeaderMapper;

    @Autowired
    private IFndDimensionService dimensionService;

    @Autowired
    private IFndDimensionValueService dimensionValueService;

    @Autowired
    private IBgtStructureLayoutService layoutService;

    @Override
    public List<BgtStructure> queryAll(IRequest request, BgtStructure bgtStructure, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        return mapper.queryAll(bgtStructure);
    }

    @Override
    public List<BgtStructure> batchUpdate(IRequest request, List<BgtStructure> list) {
        for (BgtStructure bgtStructure : list) {
            switch (bgtStructure.get__status()) {
                case DTOStatus.ADD:
                    self().insertSelective(request, bgtStructure);
                    break;
                case DTOStatus.UPDATE:
                    this.updateBgtStructure(request, bgtStructure);
                    break;
                case DTOStatus.DELETE:
                    self().deleteByPrimaryKey(bgtStructure);
                    break;
                default:
                    break;
            }
        }
        return list;

    }

    @Override
    public BgtStructure updateBgtStructure(IRequest request, BgtStructure bgtStructure) {
        BgtStructure structure = self().selectByPrimaryKey(request,
                        BgtStructure.builder().structureId(bgtStructure.getStructureId()).build());
        // 预算表一旦被预算日记账所引用，那么该预算表的编制期段不可更改或者删除
        if (budgetStructureInvokeCheck(bgtStructure.getStructureId())
                        && bgtStructure.getPeriodStrategy().equals(structure.getPeriodStrategy())) {
            throw new BgtStructureException(BgtStructureException.PERIOD_STRATEGY_ERROR, null);
        }
        mapper.updateByStructureId(bgtStructure);
        return bgtStructure;
    }

    /**
     * 检查预算表是否被预算日记账引用
     *
     * @param structureId 预算表ID
     * @author guiyuting 2019-03-05 15:48
     * @return true被引用，false未被引用
     */
    @Override
    public boolean budgetStructureInvokeCheck(Long structureId) {
        int num = journalHeaderMapper.selectCount(BgtJournalHeader.builder().scenarioId(structureId).build());
        return num == 0 ? false : true;
    }

    @Override
    public List<BgtStructure> checkBgtStructure(String controlType, String filtrateMethod, String structureCodeFrom,
                    String structureCodeTo) {
        return mapper.checkBgtStructure(controlType, filtrateMethod, structureCodeFrom, structureCodeTo);
    }

    @Override
    public List<BgtStructure> getBgtStructureByBgtOrgId(IRequest request, Long bgtOrgId) {
        BgtStructure structure = new BgtStructure();
        structure.setEnabledFlag("Y");
        structure.setBgtOrgId(bgtOrgId);
        Criteria criteria = new Criteria(structure);
        criteria.where(new WhereField(BgtStructure.FIELD_ENABLED_FLAG), new WhereField(BgtStructure.FIELD_BGT_ORG_ID));
        return selectOptions(request, structure, criteria);
    }

    @Override
    public BgtStructure getBgtStructure(IRequest request, Long structureId) {
        BgtStructure structure = new BgtStructure();
        structure.setStructureId(structureId);
        Criteria criteria = new Criteria(structure);
        criteria.where(new WhereField(BgtStructure.FIELD_STRUCTURE_ID));
        List<BgtStructure> structureList = selectOptions(request, structure, criteria);
        if (structureList != null && structureList.size() != 0) {
            return structureList.get(0);
        } else {
            return null;
        }
    }

    @Override
    public FndDimensionValue getDefaultDimensionValue(IRequest request, Long structureId, Long sequence) {
        FndDimension dimension = new FndDimension();
        dimension.setDimensionSequence(sequence);
        List<FndDimension> dimensionList = dimensionService.select(request, dimension, 0, 0);
        if (dimensionList == null || dimensionList.size() == 0) {
            return null;
        }

        dimension = dimensionList.get(0);

        BgtStructureLayout layout = new BgtStructureLayout();
        layout.setDimensionId(dimension.getDimensionId());
        layout.setStructureId(structureId);
        List<BgtStructureLayout> layoutList = layoutService.select(request, layout, 0, 0);
        if (layoutList == null || layoutList.size() == 0) {
            return null;
        }
        layout = layoutList.get(0);

        if (layout.getDefaultDimValueId() != null) {
            FndDimensionValue value = new FndDimensionValue();
            value.setDimensionValueId(layout.getDefaultDimValueId());
            Criteria criteria = new Criteria(value);
            criteria.where(new WhereField(FndDimensionValue.FIELD_DIMENSION_VALUE_ID));
            List<FndDimensionValue> valueList = dimensionValueService.selectOptions(request, value, criteria);
            if (valueList != null && valueList.size() != 0) {
                return valueList.get(0);
            }
        }

        return null;
    }

    @Override
    public List<Map> getStructureDimInfo(IRequest request, Long structureId, String position) {
        return mapper.getStructureDimInfo(structureId, position);
    }

    @Override
    public List<BgtStructure> queryForBgtJournal(IRequest request, Long bgtOrgId, Long bgtJournalTypeId) {
        return mapper.queryForBgtJournal(bgtOrgId,bgtJournalTypeId);
    }

}
