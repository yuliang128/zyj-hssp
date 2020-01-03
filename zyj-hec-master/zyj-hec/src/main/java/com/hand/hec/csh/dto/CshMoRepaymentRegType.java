package com.hand.hec.csh.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.hand.hap.core.annotation.MultiLanguage;
import com.hand.hap.core.annotation.MultiLanguageField;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.system.dto.BaseDTO;
/**
 * <p>
 *
 * </p>
 * 
 * @author jialin.xing@hand-china.com 2019/04/24 18:53
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "csh_mo_repayment_reg_type")
@MultiLanguage
public class CshMoRepaymentRegType extends BaseDTO {

    public static final String FIELD_MO_REPAYMENT_REG_TYPE_ID = "moRepaymentRegTypeId";
    public static final String FIELD_MAG_ORG_ID = "magOrgId";
    public static final String FIELD_MO_REPAYMENT_REG_TYPE_CODE = "moRepaymentRegTypeCode";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_CROSS_ENTITY_FLAG = "crossEntityFlag";
    public static final String FIELD_AUTO_AUDIT_FLAG = "autoAuditFlag";
    public static final String FIELD_ICON = "icon";
    public static final String FIELD_CAPTION_HDS_ID = "captionHdsId";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";


    @Id
    @GeneratedValue
    private Long moRepaymentRegTypeId;

    /**
     * 管理组织ID
     */
    @NotNull
    private Long magOrgId;

    /**
     * 还款申请单类型代码
     */
    @NotEmpty
    @Length(max = 30)
    private String moRepaymentRegTypeCode;

    /**
     * 还款申请单描述
     */
    @Length(max = 500)
    @MultiLanguageField
    private String description;

    /**
     * 跨实体还款标志
     */
    @NotEmpty
    @Length(max = 1)
    private String crossEntityFlag;

    /**
     * 自审核标志
     */
    @NotEmpty
    @Length(max = 1)
    private String autoAuditFlag;

    /**
     * 图标（路径/CSS类）
     */
    @Length(max = 255)
    private String icon;

    /**
     * 填写说明ID
     */
    private Long captionHdsId;

    /**
     * 启用标志
     */
    @NotEmpty
    @Length(max = 1)
    private String enabledFlag;

}
