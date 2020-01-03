package com.hand.hec.fnd.service.impl;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.Comparison;
import com.hand.hap.mybatis.common.query.WhereField;
import com.hand.hap.system.dto.DTOStatus;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.fnd.dto.FndDimension;
import com.hand.hec.fnd.dto.FndDimensionValue;
import com.hand.hec.fnd.mapper.FndCompanyDimensionValueMapper;
import com.hand.hec.fnd.service.IFndDimensionService;
import com.hand.hec.fnd.service.IFndDimensionValueService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hand.hec.fnd.dto.FndCompanyDimensionValue;
import com.hand.hec.fnd.service.IFndCompanyDimensionValueService;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * FndCompanyDimensionValueServiceImpl
 * </p>
 * 
 * @author guiyuting 2019/02/27 11:18
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class FndCompanyDimensionValueServiceImpl extends BaseServiceImpl<FndCompanyDimensionValue>
                implements IFndCompanyDimensionValueService {

    @Autowired
    private FndCompanyDimensionValueMapper dimensionValueMapper;

    @Autowired
    private IFndDimensionValueService fndDimensionValueService;

    @Autowired
    private IFndDimensionService fndDimensionService;

    @Override
    public List<FndCompanyDimensionValue> queryByDimension(IRequest request,
                    FndCompanyDimensionValue companyDimensionValue, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        return dimensionValueMapper.queryByDimension(companyDimensionValue);
    }

    @Override
    public List<FndCompanyDimensionValue> queryWithDimension(IRequest request,
                    FndCompanyDimensionValue companyDimensionValue, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        return dimensionValueMapper.queryWithDimension(companyDimensionValue);
    }

    @Override
    public List<FndCompanyDimensionValue> batchSubmit(IRequest request, List<FndCompanyDimensionValue> list) {
        List<FndCompanyDimensionValue> result = new ArrayList<>();
        for (FndCompanyDimensionValue companyDimensionValue : list) {
            switch (companyDimensionValue.get__status()) {
                case DTOStatus.ADD:
                    FndCompanyDimensionValue fndCompanyDimensionValue =
                                    insertFndCompanyDimValue(request, companyDimensionValue);
                    result.add(fndCompanyDimensionValue);
                    break;
                case DTOStatus.UPDATE:
                    FndCompanyDimensionValue fndCompanyDimensionValue2 =
                                    updateFndCompanyDimValue(request, companyDimensionValue);
                    result.add(fndCompanyDimensionValue2);
                    break;
                case DTOStatus.DELETE:
                    self().deleteByPrimaryKey(companyDimensionValue);
                    break;
                default:
                    break;
            }

        }
        return result;
    }

    @Override
    public void updateByCompanyId(IRequest request, FndCompanyDimensionValue companyDimensionValue) {
        dimensionValueMapper.updateByCompanyId(companyDimensionValue);
    }

    /**
     * 插入公司级定义维值
     *
     * @param companyDimensionValue
     * @author guiyuting 2019-03-01 15:38
     * @return
     */
    private FndCompanyDimensionValue insertFndCompanyDimValue(IRequest request,
                    FndCompanyDimensionValue companyDimensionValue) {
        FndCompanyDimensionValue result = null;
        FndDimensionValue fndDimensionValue = new FndDimensionValue();
        BeanUtils.copyProperties(companyDimensionValue,fndDimensionValue);
        fndDimensionValue.setDescription(companyDimensionValue.getDimensionValueDescription());
        fndDimensionValue = fndDimensionValueService.insertSelective(request, fndDimensionValue);

        FndDimension fndDimension = new FndDimension();
        fndDimension.setDimensionCode(companyDimensionValue.getDimensionCode());
        Criteria criteria = new Criteria(fndDimension);
        criteria.where(new WhereField(FndDimension.FIELD_DIMENSION_CODE, Comparison.EQUAL),
                        new WhereField(FndDimension.FIELD_SYSTEM_LEVEL, Comparison.IS_NULL));
        List<FndDimension> fndDimensions = fndDimensionService.selectOptions(request, fndDimension, criteria);
        if (fndDimensions != null && !fndDimensions.isEmpty()) {
            FndDimensionValue queryFndDimensionValue = new FndDimensionValue();
            queryFndDimensionValue.setDimensionValueCode(companyDimensionValue.getDimensionValueCode());
            queryFndDimensionValue.setDimensionId(fndDimensions.get(0).getDimensionId());
            Criteria cri = new Criteria(queryFndDimensionValue);
            criteria.where(new WhereField(FndDimensionValue.FIELD_DIMENSION_VALUE_CODE, Comparison.EQUAL),
                            new WhereField(FndDimensionValue.FIELD_DIMENSION_ID, Comparison.EQUAL));
            List<FndDimensionValue> fndDimensionValues =
                            fndDimensionValueService.selectOptions(request, queryFndDimensionValue, cri);
            if (fndDimensionValues != null && !fndDimensionValues.isEmpty()
                            && fndDimensions.get(0).getCompanyLevel() != null) {
                FndCompanyDimensionValue fndCompanyDimensionValue = new FndCompanyDimensionValue();
                fndCompanyDimensionValue.setCompanyId(companyDimensionValue.getCompanyId());
                fndCompanyDimensionValue.setCompanyLevel(companyDimensionValue.getCompanyLevel());
                fndCompanyDimensionValue.setDimensionValueId(fndDimensionValues.get(0).getDimensionValueId());
                fndCompanyDimensionValue.setEnabledFlag(companyDimensionValue.getEnabledFlag());
                result = self().insertSelective(request, fndCompanyDimensionValue);
            }
        }
        return result;
    }

    /**
     * 更新公司级定义维值
     *
     * @param companyDimensionValue
     * @author guiyuting 2019-03-01 15:38
     * @return
     */
    private FndCompanyDimensionValue updateFndCompanyDimValue(IRequest request,
                    FndCompanyDimensionValue companyDimensionValue) {
        int num = fndDimensionService.checkDimExists(request, companyDimensionValue.getDimensionValueId());
        if (num > 0) {
            FndDimensionValue fndDimensionValue = new FndDimensionValue();
            fndDimensionValue.setDimensionValueId(companyDimensionValue.getDimensionValueId());
            fndDimensionValue = fndDimensionValueService.selectByPrimaryKey(request,fndDimensionValue);
            fndDimensionValue.setDescription(companyDimensionValue.getDimensionValueDescription());
            fndDimensionValue.setEnabledFlag(companyDimensionValue.getEnabledFlag());
            fndDimensionValueService.updateByPrimaryKey(request, fndDimensionValue);
        }
        self().updateByCompanyId(request, companyDimensionValue);


        return companyDimensionValue;
    }

}
