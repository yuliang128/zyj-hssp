package com.hand.hec.bgt.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.hand.hap.core.annotation.MultiLanguage;
import com.hand.hap.core.annotation.MultiLanguageField;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.mybatis.common.query.Comparison;
import com.hand.hap.mybatis.common.query.Where;
import com.hand.hap.system.dto.BaseDTO;

/**
 * <p>
 * 预算表
 * </p>
 * 
 * @author mouse 2019/01/07 16:46
 */
@ExtensionAttribute(disable = true)
@Table(name = "bgt_structure")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@MultiLanguage
public class BgtStructure extends BaseDTO {

    public static final String FIELD_STRUCTURE_ID = "structureId";
    public static final String FIELD_BGT_ORG_ID = "bgtOrgId";
    public static final String FIELD_STRUCTURE_CODE = "structureCode";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_PERIOD_SET_ID = "periodSetId";
    public static final String FIELD_PERIOD_STRATEGY = "periodStrategy";
    public static final String FIELD_NOTE = "note";
    public static final String FIELD_ENTITY_FLAG = "entityFlag";
    public static final String FIELD_CENTER_FLAG = "centerFlag";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";
    public static final String FIELD_STRUCTURE_CODE_FROM = "structureCodeFrom";
    public static final String FIELD_STRUCTURE_CODE_TO = "structureCodeTo";
    public static final String FIELD_BGT_JOURNAL_TYPE_ID = "bgtJournalTypeId";
    public static final String FIELD_STRUCTURE_NAME = "structureName";

    /**
     * 月度
     */
    public static final String BUDGET_PERIOD_MONTH = "MONTH";
    /**
     * 季度
     */
    public static final String BUDGET_PERIOD_QUARTER = "QUARTER";
    /**
     * 年度
     */
    public static final String BUDGET_PERIOD_YEAR = "YEAR";


    /**
     * 主键，供其他表外键使用
     */
    @Id
    @GeneratedValue
    @Where
    private Long structureId;

    /**
     * 预算组织ID
     */
    @NotNull
    @Where
    private Long bgtOrgId;

    /**
     * 预算表代码
     */
    @NotEmpty
    @Length(max = 30)
    @Where(comparison = Comparison.LIKE)
    private String structureCode;

    /**
     * 预算表描述ID
     */
    @MultiLanguageField
    @Length(max = 500)
    @Where(comparison = Comparison.LIKE)
    private String description;

    /**
     * 预算期
     */
    @NotNull
    @Where
    private Long periodSetId;

    /**
     * 编制期段（SYSCODE：BUDGET_PERIOD）
     */
    @NotEmpty
    @Length(max = 30)
    @Where
    private String periodStrategy;

    /**
     * 备注
     */
    @Length(max = 2000)
    @Where
    private String note;

    /**
     * 预算实体标志
     */
    @NotEmpty
    @Length(max = 1)
    private String entityFlag;

    /**
     * 预算中心标志
     */
    @NotEmpty
    @Length(max = 1)
    private String centerFlag;

    /**
     * 启用标志
     */
    @NotEmpty
    @Length(max = 1)
    @Where
    private String enabledFlag;

    /**
     * 预算表代码从
     */
    @Transient
    @Where
    private String structureCodeFrom;

    /**
     * 预算表代码到
     */
    @Transient
    @Where
    private String structureCodeTo;

    @Transient
    private String bgtOrgCode;

    @Transient
    private String bgtOrgDesc;

    @Transient
    private String periodSetCode;

    @Transient
    private String periodSetDesc;

    @Transient
    @Where(expression = " and exists (select 1 from bgt_journal_type_ref_strc rs where rs.structure_id = a.structure_id and rs.bgt_journal_type_id = #{dto.bgtJournalTypeId})")
    private Long bgtJournalTypeId;

    @Transient
    private String structureName;

}
