package com.hand.hec.fnd.dto;

import javax.persistence.*;

import com.hand.hap.code.rule.dto.CodeRulesHeader;
import com.hand.hap.core.BaseConstants;
import com.hand.hap.mybatis.common.query.JoinColumn;
import com.hand.hap.mybatis.common.query.JoinOn;
import com.hand.hap.mybatis.common.query.JoinTable;
import com.hand.hap.mybatis.common.query.Where;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.system.dto.BaseDTO;
import javax.persistence.Table;
import javax.persistence.criteria.JoinType;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;

@Getter
@Setter
@ToString
@NoArgsConstructor
@ExtensionAttribute(disable = true)
@Table(name = "fnd_coding_rule_object")
public class FndCodingRuleObject extends BaseDTO {

    public static final String FIELD_CODING_RULE_OBJECT_ID = "codingRuleObjectId";
    public static final String FIELD_DOCUMENT_CATEGORY = "documentCategory";
    public static final String FIELD_PRIORITY = "priority";
    public static final String FIELD_MAG_ORG_ID = "magOrgId";
    public static final String FIELD_DOCUMENT_TYPE = "documentType";
    public static final String FIELD_COMPANY_ID = "companyId";
    public static final String FIELD_ACC_ENTITY_ID = "accEntityId";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";
    public static final String FIELD_MAG_ORG_CODE = "magOrgCode";
    public static final String FIELD_MAG_ORG_CODE_NAME = "magOrgCodeName";
    /* public static final String FIELD ="documentTypeName"; */
    public static final String FIELD_COMPANY_CODE = "companyCode";
    public static final String FIELD_COMPANY_SHORT_NAME = "companyShortName";
    public static final String FIELD_ACC_ENTITY_CODE = "accEntityCode";
    public static final String FIELD_ACC_ENTITY_NAME = "accEntityName";


    @Id
    @GeneratedValue
    @Where
    private Long codingRuleObjectId;

    /**
     * 单据类别
     */
    @NotEmpty
    @Length(max = 30)
    @Where
    private String documentCategory;

    /**
     * 优先级
     */
    @NotNull
    private Long priority;

    /**
     * 管理组织ID
     */
    private Long magOrgId;

    /**
     * 单据类型
     */
    @Length(max = 30)
    private String documentType;

    /**
     * 管理公司ID
     */
    private Long companyId;

    /**
     * 核算主体ID
     */
    private Long accEntityId;
    /**
     * 启用标志
     */
    @NotEmpty
    @Length(max = 1)
    private String enabledFlag;

    @JoinTable(name = "codeRuleJoin", target = CodeRulesHeader.class, type = JoinType.INNER,
                    joinMultiLanguageTable = true, on = {@JoinOn(joinField = CodeRulesHeader.FIELD_HEADER_ID),
                            @JoinOn(joinField = BaseDTO.FIELD_LANG, joinExpression = BaseConstants.PLACEHOLDER_LOCALE)})
    private Long codeRuleHeaderId;

    @Transient
    @JoinColumn(joinName = "codeRuleJoin", field = CodeRulesHeader.FIELD_RULE_NAME)
    private String codeRuleName;


    @Transient
    @JoinColumn(joinName = "codeRuleJoin", field = CodeRulesHeader.FIELD_RULE_CODE)
    private String codeRuleCode;

    /**
     * 管理组织代码
     */
    @Transient
    private String magOrgCode;

    /**
     * 管理组织名称
     */
    @Transient
    private String magOrgCodeName;

    /* 单据类别名称 private String documentTypeName; */


    /**
     * 公司代码
     */
    @Transient
    private String companyCode;

    /**
     * 公司名称
     */
    @Transient
    private String companyShortName;

    /**
     * 核算主体代码
     */
    @Transient
    private String accEntityCode;

    /**
     * 核算主体名称
     */
    @Transient
    private String accEntityName;

}
