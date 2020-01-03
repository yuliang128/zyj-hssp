package com.hand.hap.account.mapper;

import java.util.Arrays;
import java.util.List;

import com.hand.hap.account.dto.Role;
import com.hand.hap.account.dto.UserRole;
import com.hand.hap.core.IRequest;
import com.hand.hap.mybatis.common.Mapper;

/**
 * 用户角色Mapper.
 *
 * @author xiawang.liu@hand-china.com
 */
public interface UserRoleMapper extends Mapper<UserRole> {

    /**
     * 根据用户ID删除用户角色分配.
     *
     * @param userId 用户ID
     * @return 影响行数
     */
    int deleteByUserId(Long userId);

    /**
     * 根据用户ID、角色ID与公司ID删除用户角色分配.
     *
     * @param record 用户角色
     * @return 影响行数
     */
    int deleteByRecord(UserRole record);

    /**
     * 根据角色ID删除用户角色.
     *
     * @param roleId 角色ID
     * @return 影响行数
     */
    int deleteByRoleId(Long roleId);

    /**
     * 根据用户Id获取用户角色公司.
     *
     * @param userId 用户ID
     * @return 角色集合
     */
    List<UserRole> selectUserRoleCompanies(Long userId);


    List<UserRole> selectDistinctCompanies(Long userId);
}