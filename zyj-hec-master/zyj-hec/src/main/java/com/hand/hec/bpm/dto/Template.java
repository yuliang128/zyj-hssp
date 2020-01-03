package com.hand.hec.bpm.dto;


import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.mybatis.common.query.Comparison;
import com.hand.hap.mybatis.common.query.Where;
import com.hand.hap.system.dto.BaseDTO;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * BPM模板
 *
 * @author 18771
 */
@Table(name = "BPM_TEMPLATE")
@ExtensionAttribute(disable = true)

public class Template extends BaseDTO {

    public static final String FIELD_TEMPLATE_ID = "templateId";
    public static final String FIELD_TEMPLATE_CODE = "templateCode";
    public static final String FIELD_TEMPLATE_DESC = "templateDesc";
    public static final String FIELD_SCREEN = "screen";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";

    @Id
    @Column(name = "TEMPLATE_ID")
    @GeneratedValue
    @Where
    private Long templateId;

    @NotNull
    @Column(name = "TEMPLATE_CODE")
    @Where(comparison = Comparison.LIKE)
    private String templateCode;

    @Column(name = "TEMPLATE_DESC")
    @Where(comparison = Comparison.LIKE)
    private String templateDesc;

    @NotNull
    @Column(name = "SCREEN")
    private String screen;

    @NotNull
    @Column(name = "ENABLED_FLAG")
    private String enabledFlag;

    /**
     * @return TEMPLATE_ID
     */
    public Long getTemplateId() {
        return templateId;
    }

    /**
     * @param templateId
     */
    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }

    /**
     * @return TEMPLATE_CODE
     */
    public String getTemplateCode() {
        return templateCode;
    }

    /**
     * @param templateCode
     */
    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
    }

    /**
     * @return TEMPLATE_DESC
     */
    public String getTemplateDesc() {
        return templateDesc;
    }

    /**
     * @param templateDesc
     */
    public void setTemplateDesc(String templateDesc) {
        this.templateDesc = templateDesc;
    }

    /**
     * @return SCREEN
     */
    public String getScreen() {
        return screen;
    }

    /**
     * @param screen
     */
    public void setScreen(String screen) {
        this.screen = screen;
    }

    /**
     * @return ENABLED_FLAG
     */
    public String getEnabledFlag() {
        return enabledFlag;
    }

    /**
     * @param enabledFlag
     */
    public void setEnabledFlag(String enabledFlag) {
        this.enabledFlag = enabledFlag;
    }
}