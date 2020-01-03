package com.hand.hap.account.service;

import java.util.List;

import com.hand.hap.account.dto.Role;
import com.hand.hap.account.dto.RoleExt;
import com.hand.hap.account.dto.User;
import com.hand.hap.account.dto.UserRole;
import com.hand.hap.account.exception.RoleException;
import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;

/**
 * 角色服务接口.
 *
 * @author shengyang.zhou@hand-china.com
 * @author njq.niu@hand-china.com
 */
public interface IRoleService extends IBaseService<Role>, ProxySelf<IRoleService> {

    /**
     * 条件查询角色.
     *
     * @param requestContext 请求上下文
     * @param role           角色
     * @param pageNum        页码
     * @param pageSize       每页显示数量
     * @return 角色集合
     */
    List<Role> selectRoles(IRequest requestContext, Role role, int pageNum, int pageSize);

    /**
     * 查询不属于当前用户的角色.
     *
     * @param requestContext 请求上下文
     * @param roleExt        条件,至少包含 userId
     * @param page           页码
     * @param pageSize       每页显示数量
     * @return 角色集合
     */
    List<IRole> selectRoleNotUserRoles(IRequest requestContext, RoleExt roleExt, int page, int pageSize);

    /**
     * 查询用户所有启用状态的角色.
     *
     * @param requestContext 请求上下文
     * @param user           包含 userId
     * @return 角色集合
     */
    List<UserRole> selectRolesByUser(IRequest requestContext, User user);

    /**
     * 根据用户ID查询用户的角色.
     *
     * @param userId 用户ID
     * @return 角色集合
     */
    List<IRole> selectUserRolesByUserId(Long userId);


    /**
     * 判断用户角色分配是否存在.
     *
     * @param userId    用户ID
     * @param roleId    角色ID
     * @param companyId 公司ID
     * @throws RoleException 角色无效异常
     */
    void checkUserRoleExists(Long userId, Long roleId, Long companyId) throws RoleException;

    /**
     * 根据角色代码查询角色.
     *
     * @param roleCode 角色代码
     * @return 角色
     */
    IRole selectRoleByCode(String roleCode);

    /**
     * 查询用户的所有有效角色.
     *
     * @param requestContext 请求上下文
     * @param userRole       包含 userId
     * @return 角色集合
     */
    List<IRole> selectActiveRolesByUserAndCompany(IRequest requestContext, UserRole userRole);

    /**
     * 查询携带有效角色列表的有效公司
     *
     * @param request 请求上下文
     * @param userRole 包含UserId
     * @param roleMergeFlag 合并角色标志
     * @return java.util.List<com.hand.hap.account.dto.UserRole>
     * @author jialin.xing@hand-china.com 2019-03-12 15:52
     */
    List<UserRole> selectActiveCompaniesWithRoles(IRequest request, UserRole userRole, boolean roleMergeFlag);

    /**
     * 查询用户的有效公司&角色
     * 角色有效、公司有效、分配的角色&公司有效
     *
     * @param request 请求上下文
     * @param user    包含UserID
     * @return java.util.List<com.hand.hap.account.dto.UserRole>
     * @author jialin.xing@hand-china.com 2019-03-08 14:43
     */
    List<UserRole> selectAllActiveUserRole(IRequest request, User user);

    /**
     * 查询用户的有效公司&角色
     * 公司有效、分配的角色&公司有效
     *
     * @param request  请求上下文
     * @param userRole 包含UserID
     * @return java.util.List<com.hand.hap.account.dto.UserRole>
     * @author jialin.xing@hand-china.com 2019-03-08 14:44
     */
    List<UserRole> selectActiveCompanies(IRequest request, UserRole userRole);

}
