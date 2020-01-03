package com.hand.hec.bgt.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hand.hap.mybatis.common.Mapper;
import com.hand.hec.bgt.dto.BgtOrganization;

/**
 * <p>
 * 预算组织Mapper
 * </p>
 *
 * @author mouse 2019/01/07 16:48
 */
public interface BgtOrganizationMapper extends Mapper<BgtOrganization> {

    /**
     * 获取默认预算组织
     *
     * @param magOrgId 管理组织主键
     * @return 默认预算组织列表
     * @author rui.shi@hand-china.com 2019-01-28 15:31
     */
    public List<BgtOrganization> queryDefaultBgtOrganization(@Param("magOrgId") Long magOrgId);


    /**
     * 获取预算组织列表
     *
     * @param magOrgId 管理组织主键
     * @param parameterValue 参数：FND_ALL_ORGANIZATIONAL
     * @return 预算组织列表
     * @author rui.shi@hand-china.com 2019-01-28 15:31
     */
    public List<BgtOrganization> queryBgtOrganizationOptions(@Param("magOrgId") Long magOrgId,
            @Param("parameterValue") String parameterValue);


    /**
     * 获取所有预算组织
     *
     * @param bgtOrgCode 预算组织code
     * @param description 预算组织描述
     * @return 所有预算组织列表
     * @author ngls.luhui 2019-01-28 20:48
     */
    public List<BgtOrganization> queryBgtOrgAll(@Param("bgtOrgCode") String bgtOrgCode,
            @Param("description") String description);

    /**
     * 根据管理组织ID查询预算组织，下拉框
     *
     * @param magOrgId
     * @return java.util.List<com.hand.hec.bgt.dto.BgtOrganization> 查询到的预算组织列表
     * @author YHL 2019-02-15 20:57
     */
    List<BgtOrganization> bgtOrgOption(@Param("magOrgId") Long magOrgId);

    /**
     * 根据管理组织ID查询预算组织
     *
     * @param magOrgId
     * @author YHL 2019-02-26 20:00
     * @return com.hand.hec.bgt.dto.BgtOrganization 查询到的预算组织
     */
    BgtOrganization getBgtOrgByMagOrgId(@Param("magOrgId") Long magOrgId);

    /**
     * 获取预算项目默认预算组织
     *
     * @return 默认预算组织
     * @author xiuxian.wu 2019/2/18 16:05
     */
    List<BgtOrganization> queryDefaultBudgetOrganizationByMagOrgId();

    /**
     * 预算余额查询页面预算组织下拉框
     *
     * @author YHL 2019-03-14 14:40
     * @return java.util.List<com.hand.hec.bgt.dto.BgtOrganization> 查询到的预算组织
     */
    List<BgtOrganization> getBgtOrgOptionForBgtQuery();

    /**
     * 获取当前登录Session的管理组织关联的预算组织
     *
     *
     * @author mouse 2019-03-12 15:20
     * @return java.util.List<com.hand.hec.bgt.dto.BgtOrganization>
     */
    List<BgtOrganization> queryBgtOrgCurrentMagOrg();
}
