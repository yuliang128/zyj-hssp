package com.hand.hec.exp.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.DTOStatus;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.exp.dto.ExpMoRepEleRefLnDim;
import com.hand.hec.exp.dto.ExpMoRepTypeRefHdDim;
import com.hand.hec.exp.exception.ExpReportTypeException;
import com.hand.hec.exp.mapper.ExpMoRepEleRefLnDimMapper;
import com.hand.hec.exp.service.IExpMoRepEleRefLnDimService;
import com.hand.hec.exp.service.IExpMoRepTypeRefHdDimService;
import com.hand.hec.fnd.dto.FndDimensionValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * ExpMoRepEleRefLnDimServiceImpl
 * </p>
 *
 * @author yang.duan 2019/01/10 14:49
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ExpMoRepEleRefLnDimServiceImpl extends BaseServiceImpl<ExpMoRepEleRefLnDim> implements IExpMoRepEleRefLnDimService {
    @Autowired
    ExpMoRepEleRefLnDimMapper mapper;

    @Autowired
    IExpMoRepTypeRefHdDimService repTypeRefHdDimService;

    /**
     * <p>
     * 判断行维度是否存在且已启用(返回count)
     * <p/>
     *
     * @param ExpMoRepTypeRefHdDim dto
     * @return int
     * @author yang.duan 2019/2/26 19:51
     */
    @Override
    public int queryEleRefLnDim(ExpMoRepTypeRefHdDim dto) {
        return mapper.queryEleRefLnDim(dto);
    }


    /**
     * <p>
     * 报销单类型页面元素行维度批量提交
     * <p/>
     *
     * @param IRequest                  request
     * @param List<ExpMoRepEleRefLnDim> repEleRefLnDims
     * @return List<ExpMoRepEleRefLnDim>
     * @throws RuntimeException exception description
     * @author yang.duan 2019/2/27 10:16
     */
    @Override
    public List<ExpMoRepEleRefLnDim> batchSubmit(IRequest request, List<ExpMoRepEleRefLnDim> repEleRefLnDims) throws RuntimeException {
        Set<Long> dimensionIdSet = new HashSet<Long>();
        Set<Long> layoutSet = new HashSet<Long>();
        int check_flag = 0;
        if (!repEleRefLnDims.isEmpty()) {
            for (ExpMoRepEleRefLnDim h : repEleRefLnDims) {
                dimensionIdSet.add(h.getDimensionId());
                layoutSet.add(h.getLayoutPriority());
            }
        }
        check_flag = checkDimensionAndLay(repEleRefLnDims);
        if (checkHdDimension(repEleRefLnDims)) {
            throw new ExpReportTypeException(ExpReportTypeException.EXP_REP_TYPE_HEADER_DIM_EXIST_ERROR, null);
        }
        if (check_flag == 1 || dimensionIdSet.size() != repEleRefLnDims.size()) {
            throw new ExpReportTypeException(ExpReportTypeException.EXP_REP_TYPE_DIM_DUP_ERROR, null);
        }
        if (check_flag == 2 || layoutSet.size() != repEleRefLnDims.size()) {
            throw new ExpReportTypeException(ExpReportTypeException.EXP_REP_TYPE_ELE_DIM_LAY_DUP_ERROR, null);
        }
        return this.batchUpdate(request, repEleRefLnDims);
    }

    /**
     * <p>
     * 校验维度与布局顺序
     * <p/>
     *
     * @param List<ExpMoRepEleRefLnDim> lnDims
     * @return int
     * @author yang.duan 2019/2/27 10:32
     */
    private int checkDimensionAndLay(List<ExpMoRepEleRefLnDim> lnDims) {
        int flag_count = 0;
        int dim_count = 0;
        int layout_count = 0;
        if (!lnDims.isEmpty()) {
            for (ExpMoRepEleRefLnDim lnDim : lnDims) {
                if (lnDim.get__status().equals(DTOStatus.DELETE)) {
                    break;
                }
                ExpMoRepEleRefLnDim dim_dto = new ExpMoRepEleRefLnDim();
                dim_dto.setDimensionId(lnDim.getDimensionId());
                dim_dto.setRepPageEleRefId(lnDim.getRepPageEleRefId());

                ExpMoRepEleRefLnDim layout_dto = new ExpMoRepEleRefLnDim();
                layout_dto.setRepPageEleRefId(lnDim.getRepPageEleRefId());
                layout_dto.setLayoutPriority(lnDim.getLayoutPriority());

                dim_count = mapper.selectCount(dim_dto);
                layout_count = mapper.selectCount(layout_dto);
                if (lnDim.get__status().equals(DTOStatus.ADD)) {
                    if (dim_count > 0) {
                        flag_count = 1;
                        break;
                    }
                    if (layout_count > 0) {
                        flag_count = 2;
                        break;
                    }
                }
                if (lnDim.get__status().equals(DTOStatus.UPDATE)) {
                    ExpMoRepEleRefLnDim oldValue = mapper.selectByPrimaryKey(lnDim.getRefId());
                    if (oldValue.getDimensionId().longValue() != lnDim.getDimensionId().longValue()) {
                        if (dim_count > 0) {
                            flag_count = 1;
                            break;
                        }
                    }
                    if (oldValue.getLayoutPriority().longValue() != lnDim.getLayoutPriority().longValue()) {
                        if (layout_count > 0) {
                            flag_count = 2;
                            break;
                        }
                    }
                }
            }
        }
        return flag_count;
    }


    /**
     * <p>
     * 校验头维度是否存在且已启用
     * <p/>
     *
     * @param List<ExpMoRepEleRefLnDim> lnDimList
     * @return boolean
     * @author yang.duan 2019/2/27 10:33
     */
    private boolean checkHdDimension(List<ExpMoRepEleRefLnDim> lnDimList) {
        boolean hd_flag = false;
        int count = 0;
        if (!lnDimList.isEmpty()) {
            for (ExpMoRepEleRefLnDim refLnDim : lnDimList) {
                count = repTypeRefHdDimService.queryRepTypeRefHdDim(refLnDim);
                if (count > 0) {
                    hd_flag = true;
                    break;
                }
            }
        }
        return hd_flag;
    }

    @Override
    public List<Map> queryDftDimValue(IRequest request, Long companyId, Long bgtEntityId, Long accEntityId, Long moReportTypeId, String reportPageElementCode) {
        return mapper.queryDftDimValue(companyId, bgtEntityId, accEntityId, moReportTypeId, reportPageElementCode);
    }

}
