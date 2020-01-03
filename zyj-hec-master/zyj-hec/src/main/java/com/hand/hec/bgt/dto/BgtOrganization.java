package com.hand.hec.bgt.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.hand.hap.core.annotation.MultiLanguage;
import com.hand.hap.core.annotation.MultiLanguageField;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.mybatis.common.query.Where;
import com.hand.hap.system.dto.BaseDTO;
/**
 * <p>
 * 预算组织
 * </p>
 * 
 * @author mouse 2019/01/07 16:40
 */
@ExtensionAttribute(disable = true)
@Table(name = "bgt_organization")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@MultiLanguage
public class BgtOrganization extends BaseDTO {

    public static final String FIELD_BGT_ORG_ID = "bgtOrgId";
    public static final String FIELD_BGT_ORG_CODE = "bgtOrgCode";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_CURRENCY_CODE = "currencyCode";
    public static final String FIELD_SOURCE_TYPE_CODE = "sourceTypeCode";
    public static final String FIELD_EXCHANGE_RATE_TYPE_ID = "exchangeRateTypeId";
    public static final String FIELD_PERIOD_SET_ID = "periodSetId";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";


    /**
     * 主键，供其他表外键使用
     */
    @Id
    @GeneratedValue
    @Where
    private Long bgtOrgId;

    /**
     * 预算组织代码
     */
    @NotEmpty
    @Where
    @Length(max = 30)
    private String bgtOrgCode;

    /**
     * 预算组织描述
     */
    @NotEmpty
    @Where
    @MultiLanguageField
    @Length(max = 500)
    private String description;

    /**
     * 预算币种
     */
    @NotEmpty
    @Length(max = 30)
    private String currencyCode;

    /**
     * 来源类型（SYSCODE：BGT_ORG_SOURCE_TYPE）
     */
    @NotEmpty
    @Length(max = 30)
    private String sourceTypeCode;

    /**
     * 汇率类型ID
     */
    private Long exchangeRateTypeId;

    /**
     * 预算期ID
     */
    private Long periodSetId;

    /**
     * 启用标志
     */
    @NotEmpty
    @Length(max = 1)
    private String enabledFlag;

    /**
     * 币种名称
     */
    @Transient
    private String currencyName;

    /**
     * 来源类型描述
     */
    @Transient
    private String sourceTypeName;

    /**
     * 预算期名
     */
    @Transient
    private String periodSetName;

    /**
     * 汇率类型名称
     */
    @Transient
    private String exchangeRateTypeName;

    /**
     * 预算组织名称（描述）
     */
    @Transient
    @Length(max = 500)
    private String bgtOrgName;

    /**
     * 预算组织代码名称
     */
    @Transient
    @Length(max = 500)
    private String bgtOrgCodeName;

}
