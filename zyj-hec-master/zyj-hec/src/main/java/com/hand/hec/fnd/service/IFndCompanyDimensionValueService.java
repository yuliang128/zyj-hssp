package com.hand.hec.fnd.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.fnd.dto.FndCompanyDimensionValue;

import java.util.List;

/**
 * <p>
 * IFndCompanyDimensionValueService
 * </p>
 * 
 * @author guiyuting 2019/02/27 10:55
 */
public interface IFndCompanyDimensionValueService
                extends IBaseService<FndCompanyDimensionValue>, ProxySelf<IFndCompanyDimensionValueService> {

    /**
     * 根据维值ID和对应维度的系统级别查询
     *
     * @param companyDimensionValue
     * @author guiyuting 2019-02-27 10:58
     * @return 符合条件的公司级维值
     */
    List<FndCompanyDimensionValue> queryByDimension(IRequest request, FndCompanyDimensionValue companyDimensionValue,
                    int page, int pageSize);

    /**
     * 公司级维值定义，查询公司级维值信息
     *
     * @param companyDimensionValue
     * @author guiyuting 2019-02-28 17:19
     * @return
     */
    List<FndCompanyDimensionValue> queryWithDimension(IRequest request, FndCompanyDimensionValue companyDimensionValue,
                    int page, int pageSize);

    /**
     * 更新公司级维值
     *
     * @param list 需更新的公司级维值信息
     * @author guiyuting 2019-03-01 14:20
     * @return 已更新的公司级维值信息
     */
    List<FndCompanyDimensionValue> batchSubmit(IRequest request, List<FndCompanyDimensionValue> list);

    /**
     * 根据公司ID和维值ID 更新 启用状态
     *
     * @param null
     * @author guiyuting 2019-03-01 15:39
     * @return
     */
    void updateByCompanyId(IRequest request, FndCompanyDimensionValue companyDimensionValue);
}
