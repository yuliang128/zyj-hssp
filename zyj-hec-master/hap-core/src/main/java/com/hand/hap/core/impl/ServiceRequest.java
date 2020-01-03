/*
 * #{copyright}#
 */

package com.hand.hap.core.impl;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hand.hap.core.IRequest;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * 默认的 IRequest 实现.
 *
 * @author shengyang.zhou@hand-china.com
 */
public class ServiceRequest implements IRequest {


    private static final String ATTR_USER_ID = "_userId";

    private static final String ATTR_ROLE_ID = "_roleId";

    private static final String ATTR_COMPANY_ID = "_companyId";

    private static final String ATTR_LOCALE = "_locale";

    private static final String ATTR_MAG_ORG_ID = "_magOrgId";

    private static final String ATTR_EMP_ID = "_employeeId";

    private static final long serialVersionUID = 3699668645012922404L;

    private Long userId = -1L;
    private Long roleId = -1L;
    private Long[] roleIds = {};
    private Long companyId = -1L;
    private Long magOrgId = -1L;
    private Long employeeId = -1L;
    private String locale = Locale.getDefault().toString();
    private String employeeCode;
    private String userName;
    private String employeeName;
    private String dbType;
    private Long sessionId = 10001L;

    @JsonIgnore
    private Map<String, Object> attributeMap = new HashMap<>();

    @Override
    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String getUserName() {
        return userName;
    }

    @Override
    public Long getUserId() {
        return userId;
    }

    @Override
    public void setUserId(Long userId) {
        this.userId = userId;
        setAttribute(ATTR_USER_ID, userId);
    }

    @Override
    public String getEmployeeCode() {
        return employeeCode;
    }

    @Override
    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }

    @Override
    public String getLocale() {
        return locale;
    }

    @Override
    public void setLocale(String locale) {
        this.locale = locale;
        setAttribute(ATTR_LOCALE, locale);
    }

    @Override
    public Long getRoleId() {
        return roleId;
    }

    @Override
    public Long[] getAllRoleId() {
        return roleIds;
    }

    @Override
    public void setAllRoleId(Long[] roleIds) {
        this.roleIds = roleIds;
    }

    @Override
    public void setRoleId(Long roleId) {
        this.roleId = roleId;
        setAttribute(ATTR_ROLE_ID, roleId);
    }

    @Override
    public void setMagOrgId(Long magOrgId) {
        this.magOrgId = magOrgId;
        setAttribute(ATTR_MAG_ORG_ID, magOrgId);
    }

    @Override
    public Long getMagOrgId() {
        return this.magOrgId;
    }

    @Override
    public Long getEmployeeId() {
        return this.employeeId;
    }

    @Override
    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
        setAttribute(ATTR_EMP_ID,employeeId);
    }

    @Override
    public Long getCompanyId() {
        return companyId;
    }

    @Override
    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
        setAttribute(ATTR_COMPANY_ID, companyId);
    }

    @Override
    @SuppressWarnings("unchecked")
    @JsonAnyGetter
    public <T> T getAttribute(String name) {
        return (T) attributeMap.get(name);
    }

    @Override
    @JsonAnySetter
    public void setAttribute(String name, Object value) {
        attributeMap.put(name, value);
    }

    @Override
    @JsonIgnore
    public Map<String, Object> getAttributeMap() {
        return attributeMap;
    }

    @Override
    @JsonIgnore
    public Set<String> getAttributeNames() {
        return attributeMap.keySet();
    }

    @Override
    public String getEmployeeName() {
        return employeeName;
    }

    @Override
    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    @Override
    public String getDbType() {
        return dbType;
    }

    @Override
    public void setDbType(String dbType) {
        this.dbType = dbType;
    }

    @Override
    public Long getSessionId() {
        return sessionId;
    }

    @Override
    public void setSessionId(Long sessionId) {
        this.sessionId = sessionId;
    }
}
