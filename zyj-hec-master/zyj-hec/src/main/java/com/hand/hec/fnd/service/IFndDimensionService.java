package com.hand.hec.fnd.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.dto.CodeValue;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.fnd.dto.FndDimension;

import java.util.List;

/**
 * 维度定义Service
 *
 * @author xiuxian.wu 2019/02/18 20:09
 */
public interface IFndDimensionService extends IBaseService<FndDimension>, ProxySelf<IFndDimensionService> {
    /**
     * 根据组织级别查询公司级别
     *
     * @param dto
     * @param requestCtx
     * @return 返回code级别列表
     * @author xiuxian.wu 2019/2/18 18:17
     */
    List<CodeValue> queryCodeValue(CodeValue dto, IRequest requestCtx);

    /**
     * 系统级维值定义，查询维度代码
     *
     * @param request
     * @param dimension
     * @return 符合条件的维度
     * @author guiyuting 2019-02-26 16:46
     */
    List<FndDimension> queryForDimensionValue(IRequest request, FndDimension dimension);

    /**
     * 公司级维值定义，查询维度代码
     *
     * @param dimension
     * @return 符合条件的维度
     * @author guiyuting 2019-02-26 16:46
     */
    List<FndDimension> queryForCompanyDimensionValue(IRequest request, FndDimension dimension);

    /**
     * 根据维值ID查询对应维度是否存在
     *
     * @param null
     * @return 0表示不存在，1表示存在
     * @author guiyuting 2019-03-01 15:26
     */
    int checkDimExists(IRequest request, Long dimensionValueId);

    /**
     * 维度定义页面根据条件查询维度信息
     *
     * @param dimension
     * @author guiyuting 2019-04-01 14:43
     * @return
     */
    List<FndDimension> queryAll(IRequest request, FndDimension dimension, int page, int pageSize);


}
