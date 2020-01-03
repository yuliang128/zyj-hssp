package com.hand.hec.gld.mapper;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.gld.dto.GldAccountingEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 核算主体定义
 * </p>
 *
 * @author ngls.luhui 2019/01/08 13:52
 */
public interface GldAccountingEntityMapper extends Mapper<GldAccountingEntity> {

    /**
     * 核算主体主查询页面，查询出核算主体信息，及其默认账套信息，默认预算实体信息，关联币种信息
     *
     * @param accountingEntity
     * @return 符合要求的核算主体集合
     * @author ngls.luhui 2019-01-18 18:11
     */
    List<GldAccountingEntity> selectMore(GldAccountingEntity accountingEntity);

    /**
     * 默认核算主体定义
     *
     * @param companyId
     * @return 默认公司对应的核算主体
     * @author guiyu 2019-01-22 18:42
     */
    GldAccountingEntity queryDefaultAccEntity(@Param("companyId") Long companyId);

    /**
     * 根据核算主体（代码范围）、供应商类型ID和管理组织ID查询核算主体
     *
     * @param accountingEntity 核算主体
     * @param venderTypeId 供应商类型ID
     * @param magOrgId 管理组织ID
     * @return java.util.List<com.hand.hec.gld.dto.GldAccountingEntity> 查询到的核算主体
     * @author YHL 2019-01-30 12:49
     */
    List<GldAccountingEntity> getAccEntityByVenderTypeRefAe(
                    @Param("accountingEntity") GldAccountingEntity accountingEntity,
                    @Param("venderTypeId") Long venderTypeId, @Param("magOrgId") Long magOrgId);

    /**
     * 根据核算主体（代码范围）、管理组织ID查询核算主体
     *
     * @param accountingEntity 核算主体
     * @param magOrgId 管理组织ID
     * @return java.util.List<com.hand.hec.gld.dto.GldAccountingEntity> 查询到的核算主体
     * @author YHL 2019-02-01 16:11
     */
    List<GldAccountingEntity> getAccEntityByVenderTypeRefAeMore(
                    @Param("accountingEntity") GldAccountingEntity accountingEntity, @Param("magOrgId") Long magOrgId);

    /**
     * @Description 获取有效的核算主体
     * @Author hui.zhao01@hand-china.com
     * @Date 2019/1/30 15:06
     * @Param
     * @Version 1.0
     **/
    List<GldAccountingEntity> selectValidEntity();

    /**
     * 查询公司类型不为2的核算主体
     *
     * @param typeId 类型ID
     * @return 核算主体列表
     */
    List<GldAccountingEntity> queryAccEntityByTypeId(Long typeId);

    /**
     * 核算主体主查询页面，查询出核算主体信息，默认预算实体信息，关联币种信息
     * 
     * @return 符合要求的核算主体集合
     * @author bo.zhang 2019-03-06 18:11
     */
    List<GldAccountingEntity> selectAccEntityName();

    /**
     * 根据公司ID查询核算主体
     *
     * @param companyId 公司ID
     * @return 核算主体列表
     * @author guiyu 2019-02-19 16:11
     */
    List<GldAccountingEntity> queryAccEntityByCompany(@Param("companyId") Long companyId);

    /**
     * 根据客户ID和账户ID排除查询核算主体
     *
     * @param customerId 客户ID
     * @param accountId 账户ID
     * @return 核算主体列表
     * @author guiyu 2019-02-19 16:11
     */
    List<GldAccountingEntity> ordSystemCustomerQuery(@Param("customerId") Long customerId,
                    @Param("accountId") Long accountId, @Param("taxId") Long taxId,
                    @Param("pCustomerId") Long pCustomerId);



    /**
     * 根据账套id，查询当前账套下的默认核算主体信息
     *
     * @param setofBooksId 账套ID
     * @return 核算主体列表
     * @author ngls.luhui 2019-02-25 10:41
     */
    List<GldAccountingEntity> queryAccEntityBySob(@Param("setOfBooksId") Long setofBooksId);

    /**
     * 根据预算组织ID查询相关联的核算实体
     *
     * @param bgtOrgId 预算组织ID
     * @return
     * @author guiyuting 2019-02-25 19:46
     */
    List<GldAccountingEntity> queryByBgtOrg(@Param("bgtOrgId") Long bgtOrgId);

    /**
     * 根据税率类型ID排除查询核算主体
     *
     * @param bgtOrgId 税率类型ID
     * @return 核算主体列表
     * @author weikun.wang 2019-02-26
     */
    List<GldAccountingEntity> taxTypeQuery(@Param("taxTypeId") Long bgtOrgId);

    /**
     * 查询默认核算信息
     *
     * @return List<Map>
     * @author dingwei.ma@hand-china.com 2019-02-26
     */
    List<Map> queryForDftAccEntity();

    /**
     * 根据公司ID和管理组织ID查询(公司级维值页面)
     *
     * @param companyId 公司ID
     * @param magOrgId 管理组织ID
     * @return
     * @author guiyuting 2019-02-28 16:43
     */
    List<Map<String, Object>> queryByMagOrg(@Param("companyId") Long companyId, @Param("magOrgId") Long magOrgId);

    /**
     * 获取默认的核算主体
     *
     * @param companyId 公司ID,若为null则从request中获取
     * @return java.lang.Long
     * @author jialin.xing@hand-china.com 2019-02-28 11:24
     */
    GldAccountingEntity getDefaultAccEntity(@Param("companyId") Long companyId);



    /**
     * 根据公司id查找核算主体(更多的信息)
     *
     * @param companyId
     * @return List<Map>
     * @author ngls.luhui 2019-03-06 20:19
     */
    List<Map> queryByCompanyId(@Param("companyId") Long companyId, @Param("parameterValue") String parameterValue);

    /**
     * 根据核算主体获取默认本位币
     *
     * @param accEntityId 核算主体Id
     * @return String
     * @Author hui.zhao01@hand-china.com
     * @Date 2019/3/11 14:50
     * @Version 1.0
     **/
    String queryFuncCurrencyByEntity(@Param("accEntityId") Long accEntityId);

    /**
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
    Long getPayAccEntityId(@Param("magOrgId") Long magOrgId, @Param("companyId") Long companyId,
                    @Param("documentCategory") String documentCategory, @Param("documentTypeId") Long documentTypeId,
                    @Param("paymentMethodId") Long paymentMethodId,
                    @Param("payeeCategory") String payeeCategory);

    /*查询当前公司下符合条件的核算主体(hec_util.gld_acc_entity_company_vl_lov)
     *
     * @param dto
     * @return 符合条件的核算主体
     * @author xiuxian.wu 2019-02-03-14 11:09
     */

    List<GldAccountingEntity> queryAccEntityBySessionCompanyId(GldAccountingEntity dto);

	/**
	 * 根据账套id查询以此账套为默认账套的所有核算主体
	 * @param setOfBooksId 账套id
	 * @return
	 */
	List<GldAccountingEntity> queryGldAccountingEntityBySobId(Long setOfBooksId);

}
