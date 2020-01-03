package com.hand.hap.fnd.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.fnd.dto.FndManagingOrganization;
import com.hand.hap.system.service.IBaseService;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 管理组织service接口
 * </p>
 *
 * @author xingjialin 2019/01/07 10:38
 */
public interface IFndManagingOrganizationService extends IBaseService<FndManagingOrganization> {
    /**
     * 查询启用状态的管理组织
     * @author guiyuting 2019-01-05 20:01
     * @return
     */
    List<FndManagingOrganization> magOrgOption(IRequest request);

    /**
     * 获取默认管理组织
     * @param request
     * @return 返回默认组织信息
     * @author xiuxian.wu
     */
    FndManagingOrganization queryDefaultMagOrg(IRequest request);

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
    List<FndManagingOrganization> selectMagOrg(IRequest request);

    /**
     * 获取管理组织
     * @return 返回组织信息
     * @author bo.zhang
     */
    List<FndManagingOrganization> queryMagOrg(IRequest request);

    List<FndManagingOrganization> queryMain(FndManagingOrganization fndManagingOrganization, IRequest request,Integer page,Integer pageSize);

    /**
     * 根据公司ID查询默认管理组织
     *
     * @param companyId
     * @return 该公司对应的管理组织
     * @author guiyu 2019-01-23 16:26
     */
     FndManagingOrganization defaultManageOrganizationQuery(IRequest request, Long companyId);

    /**
     *管理组织查询条件【根据管理组织和参数 FND_ALL_ORGANIZATIONAL限制查询条件】
     * @param request
     * @return
     * @author zhongyu
     */
     List<FndManagingOrganization> queryFndMagOra(IRequest request);

    /**
     * 查询与预算组织相关联的管理组织
     *
     * @param request
     * @param bgtOrgId 预算组织ID
     * @author guiyuting 2019-03-06 9:50
     * @return
     */
    List<FndManagingOrganization> queryByBgtOrgId(IRequest request, Long bgtOrgId);


    /**
     * 查询计量单位未分配管理组织
     * @param request
     * @param uomId
     * @param dto
     * @param page
     * @param pageSize
     * @author zhongyu 2019-4-24
     * @return
     */
    List<FndManagingOrganization> queryNoneByFndUomId(IRequest request,Long uomId,FndManagingOrganization dto,int page,int pageSize);

}
