package com.hand.hec.bgt.dto;

import com.hand.hap.core.BaseConstants;
import com.hand.hap.core.annotation.MultiLanguage;
import com.hand.hap.core.annotation.MultiLanguageField;
import com.hand.hap.fnd.dto.Company;
import com.hand.hap.fnd.dto.FndCompany;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.mybatis.common.query.JoinColumn;
import com.hand.hap.mybatis.common.query.JoinOn;
import com.hand.hap.mybatis.common.query.JoinTable;
import com.hand.hap.mybatis.common.query.Where;
import com.hand.hap.system.dto.BaseDTO;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.criteria.JoinType;
import javax.validation.constraints.NotNull;

/**
 * <p>
 * 预算实体
 * </p>
 * 
 * @author mouse 2019/01/07 16:23
 */
@ExtensionAttribute(disable = true)
@Table(name = "bgt_entity")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@MultiLanguage
public class BgtEntity extends BaseDTO {

    public static final String FIELD_ENTITY_ID = "entityId";
    public static final String FIELD_BGT_ORG_ID = "bgtOrgId";
    public static final String FIELD_ENTITY_CODE = "entityCode";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_CURRENCY_CODE = "currencyCode";
    public static final String FIELD_ENTITY_TYPE = "entityType";
    public static final String FIELD_SOURCE_TYPE_CODE = "sourceTypeCode";
    public static final String FIELD_SOURCE_ID = "sourceId";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";
    public static final String FIELD_ENTITY_NAME = "entityName";
    public static final String FIELD_EXISTS_FLAG = "existsFlag";

    public static final String ENTITY_TYPE_DETAILS = "DETAILS";
    public static final String ENTITY_TYPE_SUMMARY = "SUMMARY";
    public static final String SOURCE_TYPE_MANAGEMENT = "MANAGEMENT";
    public static final String SOURCE_TYPE_ACCOUNTING = "ACCOUNTING";

    /**
     * 主键，供其他表外键使用
     */
    @Id
    @GeneratedValue
    private Long entityId;

    /**
     * 预算组织ID
     */
    @Where
    @NotNull
    private Long bgtOrgId;

    /**
     * 预算实体代码
     */
    @Where
    @NotEmpty
    @Length(max = 30)
    private String entityCode;

    @Where
    @Transient
    private String entityName;

    @Transient
    private String entityCodeFrom;

    @Transient
    private String entityCodeTo;

    /**
     * 预算实体描述ID
     */
    @Length(max = 500)
    @MultiLanguageField
    private String description;

    /**
     * 预算币种
     */
    @Length(max = 30)
    private String currencyCode;

    /**
     * 实体类型（SYSCODE：BGT_ENTITY_TYPE）
     */
    @NotEmpty
    @Length(max = 30)
    private String entityType;

    /**
     * 来源类型（SYSCODE：BGT_ORG_SOURCE_TYPE）
     */
    @NotEmpty
    @Length(max = 30)
    private String sourceTypeCode;

    /**
     * 来源明细
     */
    @JoinTable(name = "companyJoin", joinMultiLanguageTable = true, target = FndCompany.class, type = JoinType.LEFT,
                    on = {@JoinOn(joinField = FndCompany.FIELD_COMPANY_ID),
                            @JoinOn(joinField = BaseDTO.FIELD_LANG, joinExpression = BaseConstants.PLACEHOLDER_LOCALE)})
    private Long sourceId;


    @Transient
    @JoinColumn(joinName = "companyJoin", field = FndCompany.FIELD_COMPANY_FULL_NAME)
    private String sourceName;
    /**
     * 启用标志
     */
    @NotEmpty
    @Length(max = 1)
    private String enabledFlag;

    @Transient
    @Length(max = 30)
    private String bgtSourceTypeCode;

    @Transient
    @Length(max = 30)
    private String entityTypeOldValue;

    @Transient
    private Long parentEntityId;

    @Transient
    private String existsFlag;



}
