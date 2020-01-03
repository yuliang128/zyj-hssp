package com.hand.hec.exp.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.exp.dto.ExpMoRepEleRefLnDim;
import com.hand.hec.exp.dto.ExpMoRepTypeRefHdDim;
import com.hand.hec.fnd.dto.FndDimensionValue;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * IExpMoRepTypeRefHdDimService
 * </p>
 *
 * @author yang.duan 2019/01/10 14:56
 */
public interface IExpMoRepTypeRefHdDimService extends IBaseService<ExpMoRepTypeRefHdDim>, ProxySelf<IExpMoRepTypeRefHdDimService> {
    /**
     * <p>报销单类型页面元素-头维度批量提交<p/>
     *
     * @param List<ExpMoRepTypeRefHdDim> typeRefHdDims
     * @param IRequest                   request
     * @return List<ExpMoRepTypeRefHdDim>
     * @throws RuntimeException exception description
     * @author yang.duan 2019/2/26 19:38
     */
    List<ExpMoRepTypeRefHdDim> batchSubmit(IRequest request, List<ExpMoRepTypeRefHdDim> typeRefHdDims) throws RuntimeException;

    /**
     * <p>判断头维度是否存在且已启用(返回count)<p/>
     *
     * @param dto
     * @return int
     * @author yang.duan 2019/2/27 10:12
     */
    int queryRepTypeRefHdDim(ExpMoRepEleRefLnDim dto);

    /**
     * <p>
     * 获取报销单类型关联头维度的默认维值
     * <p/>
     *
     * @param request
     * @param moExpReportTypeId 报销单类型ID
     * @param sequence          维度顺序
     * @return 维值DTO
     * @author yang.duan 2019/3/21 14:27
     */
    FndDimensionValue getDftDimensionValue(IRequest request, Long moExpReportTypeId, Long sequence);


    List<Map> getHeaderDimInfo(IRequest request, Long moExpReportTypeId);
}