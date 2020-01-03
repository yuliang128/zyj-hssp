package com.hand.hap.fnd.service;

import java.util.List;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.fnd.dto.FndCompanyRefAccEntity;
import com.hand.hap.system.service.IBaseService;

/**
 * <p>
 * 管理公司关联核算主体service
 * </p>
 *
 * @author yang.duan 2019/01/10 11:09
 * @author xiuxian.wu 2019/01/25 15:08
 */
public interface IFndCompanyRefAccEntityService extends IBaseService<FndCompanyRefAccEntity>, ProxySelf<IFndCompanyRefAccEntityService> {
    /**
     * 查询是否存在符合条件的已分配给管理公司的核算主体
     *
     * @param dto 查询条件
     * @return 返回符合条件的核算主体或空
     * @author xiuxian.wu 2019/1/25 14:35
     */
    FndCompanyRefAccEntity queryFndCompanyRefAccEntity(FndCompanyRefAccEntity dto);

    /**
     * 将管理公司的默认核算主体设置为不启用及不默认
     *
     * @param companyId 管理公司ID
     * @return 返回是否成功
     * @author xiuxian.wu 2019/1/25 14:35
     */
    Long setDefaultFlagN(Long companyId);

    /**
     * 查询可以批量分配给管理公司的剩余核算主体
     *
     * @param dto      查询条件
     * @param page
     * @param pageSize
     * @param request
     * @return 返回剩余核算主体信息
     * @author xiuxian.wu 2019/1/25 14:39
     */
    List<FndCompanyRefAccEntity> queryAccEntity(FndCompanyRefAccEntity dto, IRequest request, Integer page, Integer pageSize);

    /**
     * 查询已经分配给管理公司的核算主体信息
     *
     * @param requestContext
     * @param dto            查询条件 公司ID
     * @param page
     * @param pageSize
     * @return 核算主体信息
     * @author xiuxian.wu 2019/1/25 14:42
     */
    List<FndCompanyRefAccEntity> queryAllFndCompanyRefAccEntity(IRequest requestContext, FndCompanyRefAccEntity dto, int page, int pageSize);

    /**
     * 新增或修改管理公司关联的核算主体信息及所关联的默认预算实体信息
     *
     * @param dto            新增或修改的数据
     * @param requestContext
     * @return 返回已经新增或修改的数据
     * @author xiuxian.wu 2019/1/25 14:47
     */
    List<FndCompanyRefAccEntity> submitCompanyRefAccEntity(IRequest requestContext, List<FndCompanyRefAccEntity> dto);

    /**
     * 公司Id查询默认核算主体
     *
     * @return 核算主体信息
     * @author dingwei.ma@hand-china.com 2019/1/25 14:42
     */
    Long queryDftAccByComId(IRequest request);
}