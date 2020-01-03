package com.hand.hap.account.dto;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.util.Date;
import java.util.List;

import com.hand.hap.account.service.IRole;
import com.hand.hap.system.dto.BaseDTO;

/**
 * 用户角色分配DTO.
 *
 * @author shengyang.zhou@hand-china.com
 * @date 2016/6/9
 */

@Table(name = "sys_user_role")
public class UserRole extends BaseDTO {

    private static final long serialVersionUID = 2098581833914123800L;

    public static final String FIELD_SUR_ID = "surId";
    public static final String FIELD_USER_ID = "userId";
    public static final String FIELD_ROLE_ID = "roleId";

    /**
     * 表ID，主键，供其他表做外键.
     *
     */
    @Id
    @Column
    @GeneratedValue(generator = GENERATOR_TYPE)
    private Long surId;

    @Column
    @NotNull
    private Long userId;

    @Column
    @NotNull
    private Long roleId;

    @NotNull
    private Long companyId;

    @NotNull
    private Date startActiveDate;

    private Date endActiveDate;

    @Transient
    private String userName;

    @Transient
    private String roleCode;

    @Transient
    private String roleName;

    @Transient
    private Date roleStartActiveDate;

    @Transient
    private Date roleEndActiveDate;

    @Transient
    private String companyCode;

    @Transient
    private String companyShortName;

    @Transient
    private Date companyStartActiveDate;

    @Transient
    private Date companyEndActiveDate;

    @Transient
    private List<IRole> roles;

    public Long getSurId() {
        return surId;
    }

    public void setSurId(Long surId) {
        this.surId = surId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Date getStartActiveDate() {
        return startActiveDate;
    }

    public void setStartActiveDate(Date startActiveDate) {
        this.startActiveDate = startActiveDate;
    }

    public Date getEndActiveDate() {
        return endActiveDate;
    }

    public void setEndActiveDate(Date endActiveDate) {
        this.endActiveDate = endActiveDate;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Date getRoleStartActiveDate() {
        return roleStartActiveDate;
    }

    public void setRoleStartActiveDate(Date roleStartActiveDate) {
        this.roleStartActiveDate = roleStartActiveDate;
    }

    public Date getRoleEndActiveDate() {
        return roleEndActiveDate;
    }

    public void setRoleEndActiveDate(Date roleEndActiveDate) {
        this.roleEndActiveDate = roleEndActiveDate;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getCompanyShortName() {
        return companyShortName;
    }

    public void setCompanyShortName(String companyShortName) {
        this.companyShortName = companyShortName;
    }

    public Date getCompanyStartActiveDate() {
        return companyStartActiveDate;
    }

    public void setCompanyStartActiveDate(Date companyStartActiveDate) {
        this.companyStartActiveDate = companyStartActiveDate;
    }

    public Date getCompanyEndActiveDate() {
        return companyEndActiveDate;
    }

    public void setCompanyEndActiveDate(Date companyEndActiveDate) {
        this.companyEndActiveDate = companyEndActiveDate;
    }

    public boolean isActive() {
        return (startActiveDate == null || startActiveDate.getTime() <= System.currentTimeMillis())
                && (endActiveDate == null || endActiveDate.getTime() >= System.currentTimeMillis());
    }

    public boolean roleIsActive() {
        return (roleStartActiveDate == null || roleStartActiveDate.getTime() <= System.currentTimeMillis())
                && (roleEndActiveDate == null || roleEndActiveDate.getTime() >= System.currentTimeMillis());
    }

    public boolean companyIsActive() {
        return (companyStartActiveDate == null || companyStartActiveDate.getTime() <= System.currentTimeMillis())
                && (companyEndActiveDate == null || companyEndActiveDate.getTime() >= System.currentTimeMillis());
    }

    public List<IRole> getRoles() {
        return roles;
    }

    public void setRoles(List<IRole> roles) {
        this.roles = roles;
    }
}