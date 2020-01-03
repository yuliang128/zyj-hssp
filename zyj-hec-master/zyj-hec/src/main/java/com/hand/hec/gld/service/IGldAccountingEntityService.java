package com.hand.hec.gld.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.gld.dto.GldAccountingEntity;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 核算主体定义
 * </p>
 *
 * @author ngls.luhui 2019/01/08 13:52
 */
public interface IGldAccountingEntityService
                extends IBaseService<GldAccountingEntity>, ProxySelf<IGldAccountingEntityService> {

    /**
     * 核算主体主查询页面，查询出核算主体信息，及其默认账套信息，默认预算实体信息，关联币种信息
     *
     * @param accountingEntity
     * @param request
     * @return 符合要求的核算主体集合
     * @Param page
     * @Param pageSize
     * @author ngls.luhui 2019-01-18 18:11
     */
    List<GldAccountingEntity> selectMoreAccEntity(GldAccountingEntity accountingEntity, IRequest request, Integer page,
                    Integer pageSize);


    /**
     * 默认核算主体定义
     *
     * @param companyId
     * @return 默认公司对应的核算主体
     * @author guiyu 2019-01-22 18:42
     */
    GldAccountingEntity queryDefaultAccEntity(IRequest request, Long companyId);

    /**
     * 根据核算主体（代码范围）、供应商类型ID和管理组织ID查询核算主体
     *
     * @param request
     * @param accountingEntity 核算主体
     * @param venderTypeId 供应商类型ID
     * @param magOrgId 管理组织ID
     * @param pageNum
     * @param pageSize
     * @return java.util.List<com.hand.hec.gld.dto.GldAccountingEntity> 查询到的核算主体
     * @author YHL 2019-01-30 13:35
     */
    List<GldAccountingEntity> getAccEntityByVenderTypeRefAe(IRequest request, GldAccountingEntity accountingEntity,
                    Long venderTypeId, Long magOrgId, int pageNum, int pageSize);



    /**
     * 根据核算主体（代码范围）、管理组织ID查询核算主体
     *
     * @param request
     * @param accountingEntity 核算主体
     * @param magOrgId 管理组织ID
     * @param pageNum
     * @param pageSize
     * @return java.util.List<com.hand.hec.gld.dto.GldAccountingEntity> 查询到的核算主体
     * @author YHL 2019-02-01 16:12
     */
    List<GldAccountingEntity> getAccEntityByVenderTypeRefAeMore(IRequest request, GldAccountingEntity accountingEntity,
                    Long magOrgId, int pageNum, int pageSize);



    /**
     * 根据 TypeId 查询公司类型不为 2 的核算主体
     *
     * @param request request
     * @param typeId 类型 ID
     * @return java.util.List<com.hand.hec.gld.dto.GldAccountingEntity>
     * @author jialin.xing@hand-china.com 2019-01-30 10:06
     */
    List<GldAccountingEntity> queryByTypeId(IRequest request, Long typeId);

    /**
     * 核算主体主查询页面，查询出核算主体信息，及其默认账套信息，默认预算实体信息，关联币种信息
     *
     * @return 符合要求的核算主体集合
     * @author bo.zhang 2019-03-06 18:11
     */
    List<GldAccountingEntity> selectAccEntityName(IRequest iRequest);

    /**
     * 根据公司ID查询核算主体
     *
     * @param companyId 公司ID
     * @return 核算主体列表
     * @author guiyu 2019-02-19 16:11
     */
    List<GldAccountingEntity> queryAccEntityByCompany(IRequest request, Long companyId);

    /**
     * 根据客户ID和账户ID排除查询核算主体
     *
     * @param customerId 客户ID
     * @param accountId 账户ID
     * @return 核算主体列表
     * @author guiyu 2019-02-19 16:11
     */
    List<GldAccountingEntity> ordSystemCustomerQuery(IRequest request, Long customerId, Long accountId, Long taxId,
                    Long pCustomerId);


    /**
     * 检查核算主体在指定日期是否有效
     *
     * @param date 指定日期
     * @param accEntityId 核算主体ID
     * @return
     * @author ngls.luhui 2019-02-22 12:27
     */
    Boolean checkInTime(Date date, Long accEntityId);

    /**
     * 根据预算组织ID查询相关联的核算实体
     *
     * @param bgtOrgId 预算组织ID
     * @return
     * @author guiyuting 2019-02-25 19:46
     */
    List<GldAccountingEntity> queryByBgtOrg(IRequest request, Long bgtOrgId);

    /**
     * 根据税率类型ID排除查询核算主体
     *
     * @param taxTypeId 税率类型ID
     * @return 核算主体列表
     * @author weikun.wang 2019-02-26
     */
    List<GldAccountingEntity> taxTypeQuery(IRequest request, Long taxTypeIdr,int pageNum, int pageSize);

    /**
     * 根据公司ID和管理组织ID查询(公司级维值页面)
     *
     * @param companyId 公司ID
     * @param magOrgId 管理组织ID
     * @return
     * @author guiyuting 2019-02-28 16:43
     */
    List<Map<String, Object>> queryByMagOrg(IRequest request, Long companyId, Long magOrgId);

    /**
     * 查询默认核算信息
     *
     * @return List<Map>
     * @author dingwei.ma@hand-china.com 2019-02-26
     */
    List<Map> queryForDftAccEntity(IRequest request);

    /**
     * 根据公司id查找核算主体(更多的信息)
     *
     * @param companyId
     * @return List<Map>
     * @author ngls.luhui 2019-03-06 20:19
     */
    List<Map> queryByCompanyId(Long companyId, IRequest request);

    /**
     *
     * <p>
     * 获取符合要求的付款核算主体(对应原hec_util_pkg.get_pay_acc_entity_id)
     * <p/>
     *
     * @param magOrgId 管理组织ID
     * @param companyId 管理公司ID
     * @param documentCategory 单据类别
     * @param documentTypeId 单据类型ID
     * @param payeeCategory 收款方类型
     * @param paymentMethodId 收款方式ID
     * @return 符合条件的核算主体ID
     * @author yang.duan 2019/3/13 19:22
     */
    Long getPayAccEntityId(Long magOrgId, Long companyId, String documentCategory, Long documentTypeId,
                    Long paymentMethodId, String payeeCategory);

    /*
     * 查询当前公司下符合条件的核算主体(hec_util.gld_acc_entity_company_vl_lov)
     *
     * @param dto
     * @param request
     * @param page
     * @param pageSize
     * @return 符合条件的核算主体
     * @author xiuxian.wu 2019-02-03-14 11:09
     */

    List<GldAccountingEntity> queryAccEntityBySessionCompanyId(GldAccountingEntity dto, IRequest request, int page,
                    int pageSize);
}
