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
 * IExpMoRepEleRefLnDimService
 * </p>
 *
 * @author yang.duan 2019/01/10 14:52
 */
public interface IExpMoRepEleRefLnDimService
                extends IBaseService<ExpMoRepEleRefLnDim>, ProxySelf<IExpMoRepEleRefLnDimService> {
    /**
     * <p>
     * 判断行维度是否存在且已启用(返回count)
     * <p/>
     *
     * @param ExpMoRepTypeRefHdDim dto
     * @return int
     * @author yang.duan 2019/2/26 19:51
     */
    int queryEleRefLnDim(ExpMoRepTypeRefHdDim dto);

    /**
     * <p>
     * 报销单类型页面元素行维度批量提交
     * <p/>
     * 
     * @param IRequest request
     * @param List<ExpMoRepEleRefLnDim> repEleRefLnDims
     * @return List<ExpMoRepEleRefLnDim>
     * @throws RuntimeException exception description
     * @author yang.duan 2019/2/27 10:16
     */
    List<ExpMoRepEleRefLnDim> batchSubmit(IRequest request, List<ExpMoRepEleRefLnDim> repEleRefLnDims)
                    throws RuntimeException;

    List<Map> queryDftDimValue(IRequest request, Long companyId, Long bgtEntityId,Long accEntityId, Long moReportTypeId,
                    String reportPageElementCode);


}
