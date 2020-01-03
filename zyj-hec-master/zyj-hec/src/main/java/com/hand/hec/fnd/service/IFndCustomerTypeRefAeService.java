package com.hand.hec.fnd.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.fnd.dto.FndCustomerTypeRefAe;
import com.hand.hec.gld.dto.GldAccountingEntity;

import java.util.List;

/**
 * <p>
 * 客户类型关联核算主体Service
 * </p>
 *
 * @author xiuxian.wu 2019/02/14 11:07
 */
public interface IFndCustomerTypeRefAeService extends IBaseService<FndCustomerTypeRefAe>, ProxySelf<IFndCustomerTypeRefAeService> {
    /**
     * 查询所有符合条件可以分配的核算主体
     *
     * @param dto 条件
     * @param page
     * @param pageSize
     * @param request
     * @return 返回符合条件的核算主体
     * @author xiuxian.wu 2019/2/15 10:16
     */
    List<GldAccountingEntity> queryAllAccountingEntity(FndCustomerTypeRefAe dto , IRequest request, Integer page, Integer pageSize);

    /**
     * 批量插入客户类型与核算主体关联关系
     *
     * @param request
     * @param list
     * @return 插入数据
     * @author xiuxian.wu 2019/2/15 16:16
     */
    List<FndCustomerTypeRefAe> insertAccEntities(IRequest request, List<FndCustomerTypeRefAe> list);
}