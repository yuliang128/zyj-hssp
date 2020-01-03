package com.hand.hec.exp.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.DTOStatus;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import com.hand.hec.exp.dto.ExpMoRepEleRefLnDim;
import com.hand.hec.exp.dto.ExpMoRepTypeRefHdDim;
import com.hand.hec.exp.exception.ExpReportTypeException;
import com.hand.hec.exp.mapper.ExpMoRepTypeRefHdDimMapper;
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
 * ExpMoRepTypeRefHdDimServiceImpl
 * </p>
 *
 * @author yang.duan 2019/01/10 14:50
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ExpMoRepTypeRefHdDimServiceImpl extends BaseServiceImpl<ExpMoRepTypeRefHdDim>
                implements IExpMoRepTypeRefHdDimService {
    @Autowired
    ExpMoRepTypeRefHdDimMapper mapper;

    @Autowired
    IExpMoRepEleRefLnDimService lnDimService;

    @Override
    public int queryRepTypeRefHdDim(ExpMoRepEleRefLnDim dto) {
        return mapper.queryRepTypeRefHdDim(dto);
    }

    /**
     * <p>
     * 报销单类型页面元素-头维度批量提交
     * <p/>
     * 
     * @param List<ExpMoRepTypeRefHdDim> typeRefHdDims
     * @param IRequest request
     * @return List<ExpMoRepTypeRefHdDim>
     * @throws RuntimeException exception description
     * @author yang.duan 2019/2/26 19:38
     */
    @Override
    public List<ExpMoRepTypeRefHdDim> batchSubmit(IRequest request, List<ExpMoRepTypeRefHdDim> typeRefHdDims)
                    throws RuntimeException {
        Set<Long> dimensionIdSet = new HashSet<Long>();
        Set<Long> layoutSet = new HashSet<Long>();
        int check_flag = 0;
        if (!typeRefHdDims.isEmpty()) {
            for (ExpMoRepTypeRefHdDim h : typeRefHdDims) {
                dimensionIdSet.add(h.getDimensionId());
                layoutSet.add(h.getLayoutPriority());
            }
        }
        check_flag = checkDimensionAndLay(typeRefHdDims);
        if (checkLnDimension(typeRefHdDims)) {
            //throw new RuntimeException("维度已分配至页面元素中，请确认！");
            throw new ExpReportTypeException(ExpReportTypeException.EXP_REP_TYPE_DIM_EXIST_ERROR,null);
        }
        if (check_flag == 1 || dimensionIdSet.size() != typeRefHdDims.size()) {
            throw new ExpReportTypeException(ExpReportTypeException.EXP_REP_TYPE_DIM_DUP_ERROR,null);
        }
        if (check_flag == 2 || layoutSet.size() != typeRefHdDims.size()) {
            throw new ExpReportTypeException(ExpReportTypeException.EXP_REP_TYPE_ELE_DIM_LAY_DUP_ERROR,null);
        }
        return this.batchUpdate(request, typeRefHdDims);
    }

    /**
     * <p>
     * 校验维度与布局顺序
     * <p/>
     *
     * @param List<ExpMoRepTypeRefHdDim> dims
     * @return int
     * @author yang.duan 2019/2/27 10:31
     */
    private int checkDimensionAndLay(List<ExpMoRepTypeRefHdDim> dims) {
        int flag_count = 0;
        int dim_count = 0;
        int layout_count = 0;
        if (!dims.isEmpty()) {
            for (ExpMoRepTypeRefHdDim hdDim : dims) {
                if (hdDim.get__status().equals(DTOStatus.DELETE)) {
                    break;
                }
                ExpMoRepTypeRefHdDim dim_dto = new ExpMoRepTypeRefHdDim();
                dim_dto.setDimensionId(hdDim.getDimensionId());
                dim_dto.setMoExpReportTypeId(hdDim.getMoExpReportTypeId());

                ExpMoRepTypeRefHdDim layout_dto = new ExpMoRepTypeRefHdDim();
                layout_dto.setMoExpReportTypeId(hdDim.getMoExpReportTypeId());
                layout_dto.setLayoutPriority(hdDim.getLayoutPriority());

                dim_count = mapper.selectCount(dim_dto);
                layout_count = mapper.selectCount(layout_dto);
                if (hdDim.get__status().equals(DTOStatus.ADD)) {
                    if (dim_count > 0) {
                        flag_count = 1;
                        break;
                    }
                    if (layout_count > 0) {
                        flag_count = 2;
                        break;
                    }
                }
                if (hdDim.get__status().equals(DTOStatus.UPDATE)) {
                    ExpMoRepTypeRefHdDim oldValue = mapper.selectByPrimaryKey(hdDim.getRefId());
                    if (oldValue.getDimensionId().longValue() != hdDim.getDimensionId().longValue()) {
                        if (dim_count > 0) {
                            flag_count = 1;
                            break;
                        }
                    }
                    if (oldValue.getLayoutPriority().longValue() != hdDim.getLayoutPriority().longValue()) {
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
     * 校验行维度是否存在且已启用
     * <p/>
     *
     * @param List<ExpMoRepTypeRefHdDim> dimList
     * @return boolean
     * @author yang.duan 2019/2/27 10:31
     */
    private boolean checkLnDimension(List<ExpMoRepTypeRefHdDim> dimList) {
        boolean ln_flag = false;
        int count = 0;
        if (!dimList.isEmpty()) {
            for (ExpMoRepTypeRefHdDim refHdDim : dimList) {
                count = lnDimService.queryEleRefLnDim(refHdDim);
                if (count > 0) {
                    ln_flag = true;
                    break;
                }
            }
        }
        return ln_flag;
    }

    /**
     * <p>
     * 获取报销单类型关联头维度的默认维值
     * <p/>
     * 
     * @param request
     * @param moExpReportTypeId 报销单类型ID
     * @param sequence 维度顺序
     * @return 维值DTO
     * @author yang.duan 2019/3/21 14:27
     */
    @Override
    public FndDimensionValue getDftDimensionValue(IRequest request, Long moExpReportTypeId, Long sequence) {
        return mapper.getDftDimensionValue(moExpReportTypeId, sequence);
    }

    @Override
    public List<Map> getHeaderDimInfo(IRequest request, Long moExpReportTypeId){
        return mapper.getHeaderDimInfo(moExpReportTypeId);
    }
}
