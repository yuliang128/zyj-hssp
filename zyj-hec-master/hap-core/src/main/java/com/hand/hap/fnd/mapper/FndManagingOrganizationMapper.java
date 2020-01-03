package com.hand.hap.fnd.mapper;

import com.hand.hap.core.IRequest;
import com.hand.hap.fnd.dto.FndManagingOrganization;
import com.hand.hap.mybatis.common.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


/**
 * <p>
 * 管理组织Mapper接口
 * </p>
 *
 * @author xingjialin 2019/01/07 10:37
 */
public interface FndManagingOrganizationMapper extends Mapper<FndManagingOrganization> {
    /**
     * 查询启用状态的管理组织
     *
     * @param null
     * @author guiyuting 2019-01-05 20:01
     * @return 
     */
    List<FndManagingOrganization> magOrgOption();

    /**
     * 获取默认管理组织
     *
     * @return 返回默认组织信息
     * @author xiuxian.wu
     */
    FndManagingOrganization queryDefaultMagOrg();

    /**
     * 查询管理组织及其默认预算组织，账套的信息
     *
     * @param fndManagingOrganization
     * @return
     * @author ngls.luhui 2019-02-15 12:17
     */
    List<FndManagingOrganization> queryMain(FndManagingOrganization fndManagingOrganization);

    /**
     * 工作中心定义 获取所有管理组织
     * @return 返回组织信息
     * @author bo.zhang
     */
    List<FndManagingOrganization> selectMagOrg();

    /**
     * 获取管理组织
     * @param parameterValue
     * @return 返回组织信息
     * @author bo.zhang
     */
    List<FndManagingOrganization> queryMagOrg(@Param("parameterValue") String parameterValue);

    /**
     * 获取系统参数的值
     * @param parameterCode
     * @return List<FndManagingOrganization>
     * @author bo.zhang  2019-03-28
     */
    List<FndManagingOrganization> querySystemValue(@Param("parameterCode") String parameterCode);


    /**
     * 查询管理组织
     *
     * @param paramValue
     * @param magOrgId
     * @return
     * @author Zhongyu 2019-2-19 14:35
     */
    List<FndManagingOrganization> queryFndManOrg(@Param("paramValue") String paramValue,
                                                 @Param("magOrgId") Long magOrgId);

    /**
     * 根据公司ID查询默认管理组织
     *
     * @param companyId
     * @return 该公司对应的管理组织
     * @author guiyu 2019-01-23 16:26
     */
    FndManagingOrganization defaultManageOrganizationQuery(@Param("companyId") Long companyId);

    /**
     * 查询与预算组织相关联的管理组织
     *
     * @param bgtOrgId 预算组织ID
     * @author guiyuting 2019-03-06 9:50
     * @return 
     */
    List<FndManagingOrganization> queryByBgtOrgId(@Param("bgtOrgId") Long bgtOrgId);

    /**
     * 查询计量单位未分配管理组织
     * @param fndUomId
     * @param dto
     * @author zhongyu 2019-4-24
     * @return
     */
    List<FndManagingOrganization> queryNoneByFndUomId(@Param("fndUomId") Long fndUomId,@Param("dto") FndManagingOrganization dto);
}

