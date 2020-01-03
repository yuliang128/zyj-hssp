package com.hand.hec.bgt.service;

import java.util.List;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.hand.hec.bgt.dto.BgtOrganization;

/**
 * <p>
 * 预算组织Service
 * </p>
 *
 * @author mouse 2019/01/07 17:00
 */
public interface IBgtOrganizationService extends IBaseService<BgtOrganization>, ProxySelf<IBgtOrganizationService> {

    /**
     * 获取默认预算组织
     *
     * @param request request
     * @return
     * @author rui.shi@hand-china.com 2019-01-28 15:31
     */
    List<BgtOrganization> queryDefaultBgtOrganization(IRequest request);


    /**
     * 获取下拉框中的预算组织列表
     *
     * @param request request
     * @return
     * @author rui.shi@hand-china.com 2019-01-28 15:31
     */
    List<BgtOrganization> queryBgtOrganizationOptions(IRequest request);

    /**
     * 获取所有预算组织
     *
     * @param bgtOrgCode
     * @param description
     * @param page
     * @param pageSize
     * @return 所有预算组织列表
     * @author ngls.luhui 2019-01-28 20:48
     */
    List<BgtOrganization> queryBgtOrgAll(String bgtOrgCode, String description, IRequest requestContext,Integer page,Integer pageSize);

    /**
     * 根据管理组织ID查询预算组织，下拉框
     *
     * @param request
     * @param magOrgId 管理组织ID
     * @return java.util.List<com.hand.hec.bgt.dto.BgtOrganization> 查询到的预算组织列表
     * @author YHL 2019-02-15 21:01
     */
    List<BgtOrganization> bgtOrgOption(IRequest request, Long magOrgId);

    /**
     * 根据管理组织ID查询预算组织
     *
     * @param request
     * @param magOrgId 管理组织ID
     * @return com.hand.hec.bgt.dto.BgtOrganization 查询到的预算组织
     * @author YHL 2019-02-26 20:02
     */
    BgtOrganization getBgtOrgByMagOrgId(IRequest request, Long magOrgId);

    /**
     * 获取预算项目默认预算组织
     *
     * @param requestContext
     * @return 默认预算组织
     * @author xiuxian.wu 2019/2/18 16:05
     */

    List<BgtOrganization> queryDefaultBudgetOrganizationByMagOrgId(IRequest requestContext);

    /**
     * 获取所有的预算组织
     *
     * @param condition
     * @param request
     * @return 返回所有的预算组织
     * @author xiuxian.wu 2019/2/18 16:06
     */
    List<BgtOrganization> queryAll(IRequest request, BgtOrganization condition);

    /**
     * 预算余额查询页面预算组织下拉框
     *
     * @param request
     * @author YHL 2019-03-14 14:41
     * @return java.util.List<com.hand.hec.bgt.dto.BgtOrganization> 查询到的预算组织
     */
    List<BgtOrganization> getBgtOrgOptionForBgtQuery(IRequest request);

    /**
     * 获取当前登录Session的管理组织关联的预算组织
     *
     *
     * @author mouse 2019-03-12 15:22
     * @return java.util.List<com.hand.hec.bgt.dto.BgtOrganization>
     */
    List<BgtOrganization> queryBgtOrgCurrentMagOrg(IRequest request);
}
