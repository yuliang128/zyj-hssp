package com.hand.hec.bpm.dto;

/**
 * Auto Generated By Code Generator
 * Description:
 */

import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.mybatis.common.query.Comparison;
import com.hand.hap.mybatis.common.query.JoinCode;
import com.hand.hap.mybatis.common.query.Where;
import com.hand.hap.system.dto.BaseDTO;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.stereotype.Component;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Component
@ExtensionAttribute(disable = true)
@Table(name = "bpm_page")
public class Page extends BaseDTO {

    public static final String FIELD_PAGE_ID = "pageId";
    public static final String FIELD_PAGE_TYPE = "pageType";
    public static final String FIELD_PAGE_CODE = "pageCode";
    public static final String FIELD_PAGE_DESC = "pageDesc";
    public static final String FIELD_GROUP_ID = "groupId";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";


    @Id
    @GeneratedValue
    @Where
    private Long pageId;//页面ID，主键

    @NotEmpty
    @Length(max = 200)
    @Where
    private String pageType;//页面类型,新增，维护，查看，审批，其中前三项每个页面组下只能有一个，归提单人使用

    @Transient
    @JoinCode(code = "BPM.PAGE_TYPE", joinKey = Page.FIELD_PAGE_TYPE)
    private String pageTypeDesc;

    @NotEmpty
    @Length(max = 200)
    @Where(comparison = Comparison.LIKE)
    private String pageCode;//页面代码

    @Length(max = 200)
    @Where(comparison = Comparison.LIKE)
    private String pageDesc;//页面描述

    @Where
    private Long groupId;//所属页面组ID

    @Length(max = 200)
    private String enabledFlag;//启用


    public void setPageId(Long pageId) {
        this.pageId = pageId;
    }

    public Long getPageId() {
        return pageId;
    }

    public void setPageType(String pageType) {
        this.pageType = pageType;
    }

    public String getPageType() {
        return pageType;
    }

    public void setPageCode(String pageCode) {
        this.pageCode = pageCode;
    }

    public String getPageCode() {
        return pageCode;
    }

    public void setPageDesc(String pageDesc) {
        this.pageDesc = pageDesc;
    }

    public String getPageDesc() {
        return pageDesc;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setEnabledFlag(String enabledFlag) {
        this.enabledFlag = enabledFlag;
    }

    public String getEnabledFlag() {
        return enabledFlag;
    }

    public String getPageTypeDesc() {
        return pageTypeDesc;
    }

    public void setPageTypeDesc(String pageTypeDesc) {
        this.pageTypeDesc = pageTypeDesc;
    }
}
