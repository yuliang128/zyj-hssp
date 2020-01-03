package com.hand.hap.account.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.StreamUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hand.hap.account.dto.Role;
import com.hand.hap.account.dto.RoleExt;
import com.hand.hap.account.dto.User;
import com.hand.hap.account.dto.UserRole;
import com.hand.hap.account.exception.RoleException;
import com.hand.hap.account.mapper.RoleMapper;
import com.hand.hap.account.mapper.UserRoleMapper;
import com.hand.hap.account.service.IRole;
import com.hand.hap.account.service.IRoleService;
import com.hand.hap.account.service.IUserService;
import com.hand.hap.cache.Cache;
import com.hand.hap.cache.CacheManager;
import com.hand.hap.core.BaseConstants;
import com.hand.hap.core.IRequest;
import com.hand.hap.core.annotation.StdWho;
import com.hand.hap.function.service.IRoleFunctionService;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.Comparison;
import com.hand.hap.mybatis.common.query.WhereField;
import com.hand.hap.system.service.impl.BaseServiceImpl;

/**
 * 角色服务接口 - 实现类.
 *
 * @author shengyang.zhou@hand-china.com
 * @author njq.niu@hand-china.com
 */
@Service
public class RoleServiceImpl extends BaseServiceImpl<Role> implements IRoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private IRoleFunctionService roleFunctionService;

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private IUserService iUserService;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Role> selectRoles(IRequest request, Role role, int pageNum, int pageSize) {
        Criteria criteria = new Criteria(role);
        criteria.where(Role.FIELD_ROLE_ID, new WhereField(Role.FIELD_ROLE_CODE, Comparison.LIKE),
                new WhereField(Role.FIELD_ROLE_NAME, Comparison.LIKE),
                new WhereField(Role.FIELD_ROLE_DESCRIPTION, Comparison.LIKE));
        return super.selectOptions(request, role, criteria, pageNum, pageSize);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<IRole> selectRoleNotUserRoles(IRequest request, RoleExt roleExt, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        List list = roleMapper.selectRoleNotUserRoles(roleExt);
        return list;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<UserRole> selectRolesByUser(IRequest requestContext, User user) {
        return userRoleMapper.selectUserRoleCompanies(user.getUserId());
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<IRole> selectUserRolesByUserId(Long userId) {
        List rootRoles = roleMapper.selectUserRolesByUserId(userId);
        return rootRoles;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,rollbackFor = Exception.class)
    public void checkUserRoleExists(Long userId, Long roleId,Long companyId) throws RoleException {
        if (roleMapper.selectUserRoleCount(userId, roleId,companyId) < 1) {
            throw new RoleException(RoleException.MSG_INVALID_USER_ROLE, RoleException.MSG_INVALID_USER_ROLE, null);
        }
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public IRole selectRoleByCode(String roleCode) {
        IRole role = getRoleCache().getValue(roleCode);
        if (role == null) {
            Role record = new Role();
            record.setRoleCode(roleCode);
            role = roleMapper.selectOne(record);
            if (role != null) {
                getRoleCache().setValue(roleCode, role);
            }
        }
        return role;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<IRole> selectActiveRolesByUserAndCompany(IRequest requestContext, UserRole userRole) {
        User user = new User();
        if (null == userRole.getRoleCode()) {
            user = iUserService.selectByUserRole(userRole);
        }

        List<IRole> roles = new ArrayList<>();
        for (String roleCode : user.getRoleCode()) {
            roles.add(selectRoleByCode(roleCode));
        }

        return roles.stream().filter(IRole::isActive).collect(Collectors.toList());
    }

    @Override
    public List<UserRole> selectAllActiveUserRole(IRequest request, User user) {
        return selectRolesByUser(request,user)
                .stream()
                .filter(UserRole::isActive)
                .filter(UserRole::companyIsActive)
                .filter(UserRole::roleIsActive)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserRole> selectActiveCompanies(IRequest request, UserRole userRole) {
        return userRoleMapper.selectDistinctCompanies(userRole.getUserId())
                .stream()
                .filter(UserRole::isActive)
                .filter(UserRole::companyIsActive)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserRole> selectActiveCompaniesWithRoles(IRequest request, UserRole userRole, boolean roleMergeFlag) {
        List<UserRole> activeCompanies = selectActiveCompanies(request,userRole);
        if (roleMergeFlag) {
            return activeCompanies;
        }
        activeCompanies.forEach(company -> {
            userRole.setCompanyId(company.getCompanyId());
            company.setRoles(selectActiveRolesByUserAndCompany(request,userRole));
        });
        return activeCompanies;
    }

    @Override
    protected boolean useSelectiveUpdate() {
        return false;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteByPrimaryKey(Role record) {
        int ret = super.deleteByPrimaryKey(record);
        userRoleMapper.deleteByRoleId(record.getRoleId());
        roleFunctionService.removeRoleFunctionByRoleId(record.getRoleId());
        getRoleCache().remove(record.getRoleCode());
        return ret;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Role updateByPrimaryKey(IRequest request, @StdWho Role record) {
        int count = roleMapper.updateByPrimaryKey(record);
        checkOvn(count, record);
        getRoleCache().remove(record.getRoleCode());
        getRoleCache().setValue(record.getRoleCode(), record);
        return record;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Role insertSelective(IRequest request, Role record) {
        int count = roleMapper.insertSelective(record);
        checkOvn(count, record);
        getRoleCache().setValue(record.getRoleCode(), record);
        return record;
    }

    /**
     * 获取角色redis缓存.
     *
     * @return 角色redis缓存
     */
    private Cache<IRole> getRoleCache() {
        return cacheManager.getCache(BaseConstants.CACHE_ROLE_CODE);
    }
}
