package com.hand.hap.account.dto;

import javax.persistence.Transient;
import java.util.Date;

/**
 * 角色扩展类.
 *
 * @author shengyang.zhou@hand-china.com
 * @date 2016/6/9
 */

public class RoleExt extends Role {
    private Long surId;

    private Long userId;

    private Long companyId;

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

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

}
