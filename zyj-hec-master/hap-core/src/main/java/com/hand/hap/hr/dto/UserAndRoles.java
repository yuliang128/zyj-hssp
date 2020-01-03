package com.hand.hap.hr.dto;

import java.util.List;

import com.hand.hap.account.dto.User;
import com.hand.hap.account.dto.UserRole;

/**
 * 用户角色对象.
 *
 * @author jialong.zuo@hand-china.com
 * @date 2016/12/21.
 */
public class UserAndRoles {

    User user;
    List<UserRole> roles;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<UserRole> getRoles() {
        return roles;
    }

    public void setRoles(List<UserRole> roles) {
        this.roles = roles;
    }
}
