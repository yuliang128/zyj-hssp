package com.hand.hec.bgt.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.hand.hap.mybatis.common.query.Comparison;
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
 * 预算场景
 * </p>
 *
 * @author mouse 2019/01/07 16:46
 */
@ExtensionAttribute(disable = true)
@Table(name = "bgt_scenario")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@MultiLanguage
public class BgtScenario extends BaseDTO {

    public static final String FIELD_SCENARIO_ID = "scenarioId";
    public static final String FIELD_BGT_ORG_ID = "bgtOrgId";
    public static final String FIELD_SCENARIO_CODE = "scenarioCode";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_NOTES = "notes";
    public static final String FIELD_DEFAULT_FLAG = "defaultFlag";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";
    public static final String FIELD_SCENARIO_CODE_FROM = "scenarioCodeFrom";
    public static final String FIELD_SCENARIO_CODE_TO = "scenarioCodeTo";
    public static final String FIELD_BGT_JOURNAL_TYPE_ID = "bgtJournalTypeId";


    /**
     * 主键，供其他表外键使用
     */
    @Id
    @GeneratedValue
    @Where
    private Long scenarioId;

    /**
     * 预算组织ID
     */
    @NotNull
    @Where
    private Long bgtOrgId;

    /**
     * 预算场景代码
     */
    @NotEmpty
    @Length(max = 30)
    @Where(comparison = Comparison.LIKE)
    private String scenarioCode;

    /**
     * 预算场景描述
     */
    @MultiLanguageField
    @Where
    @NotEmpty
    @Length(max = 500)
    private String description;

    /**
     * 备注
     */
    @Length(max = 2000)
    @Where(comparison = Comparison.LIKE)
    private String notes;

    /**
     * 默认场景
     */
    @NotEmpty
    @Length(max = 1)
    @Where
    private String defaultFlag;

    /**
     * 启用标志
     */
    @NotEmpty
    @Length(max = 1)
    @Where
    private String enabledFlag;

    @Transient
    private String scenarioCodeFrom;

    @Transient
    private String scenarioCodeTo;

    @Transient
    @Where(expression = " and exists (select 1 from bgt_journal_type_ref_scnr rsn where rsn.scenario_id = a.scenario_id and rsn.bgt_journal_type_id = #{dto.bgtJournalTypeId})")
    private Long bgtJournalTypeId;
}
