package com.hand.hec.gld.dto;

import com.hand.hap.core.BaseConstants;
import com.hand.hap.core.annotation.MultiLanguage;
import com.hand.hap.core.annotation.MultiLanguageField;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.mybatis.common.query.*;
import com.hand.hap.system.dto.BaseDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.criteria.JoinType;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
@NoArgsConstructor
@MultiLanguage
@ExtensionAttribute(disable = true)
@Table(name = "gld_mapping_cond_grp_hd")
public class GldMappingCondGrpHd extends BaseDTO {

    public static final String COND_GRP_HD_ID = "condGrpHdId";
    public static final String FIELD_USAGE_CODE = "usageCode";
    public static final String FIELD_GROUP_NAME = "groupName";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_PRIORITY = "priority";
    public static final String FIELD_TABLE_NAME = "tableName";
    public static final String FIELD_SYS_FLAG = "sysFlag";
    public static final String FIELD_USAGE_CODE_DESC = "usageCodeDesc";


    /**
     * 用途代码
     */
    @NotEmpty
    @Length(max = 30)
    @JoinTable(name = "usageCodeJoin",type = JoinType.LEFT,joinMultiLanguageTable = true,target = GldUsageCode.class,on = {@JoinOn(joinField = GldUsageCode.FIELD_USAGE_CODE), @JoinOn(joinField = BaseDTO.FIELD_LANG, joinExpression = BaseConstants.PLACEHOLDER_LOCALE)})
    @Where
    private String usageCode;

    @Id
    @GeneratedValue
    private Long condGrpHdId;

    /**
     * 描述
     */
    @NotEmpty
    @Length(max = 240)
    @Where(comparison = Comparison.LIKE)
    private String groupName;

    /**
     * 描述
     */
    @NotEmpty
    @Length(max = 500)
    @MultiLanguageField
    @Where(comparison = Comparison.LIKE)
    private String description;

    /**
     * 优先级
     */
    @NotNull
    private Long priority;

    /**
     * 表名
     */
    @NotEmpty
    @Length(max = 30)
    private String tableName;

    /**
     * 系统标志
     */
    @NotEmpty
    @Length(max = 1)
    private String sysFlag;

    /**
     * 账套ID
     */
    @Transient
    private Long setOfBooksId;

    /**
     * 管理组织ID
     */
    @Transient
    private Long magOrgId;

    /**
     * 查询条件
     */
    @Transient
    private String whereClause;

    /**
     * 动态生成表的行ID
     */
    @Transient
    private Long lineId;

    /**
     * 动态生成表的科目ID
     */
    @Transient
    private Long accountId;

    /**
     * 动态生成表的科目代码
     */
    @Transient
    private String accountCode;

    /**
     * 动态生成表的匹配项
     */
    @Transient
    private String mappingCondition;

    /**
     * 动态生成表的匹配项对应的值
     */
    @Transient
    private String mappingConditionValue;

    /**
     * 用途代码描述
     */
    @Transient
    @JoinColumn(joinName = "usageCodeJoin",field = GldUsageCode.FIELD_DESCRIPTION)
    private String usageCodeDesc;
}
