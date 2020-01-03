package com.hand.hec.bgt.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.hand.hap.core.BaseConstants;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.mybatis.common.query.JoinColumn;
import com.hand.hap.mybatis.common.query.JoinOn;
import com.hand.hap.mybatis.common.query.JoinTable;
import com.hand.hap.mybatis.common.query.Where;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Table;

import com.hand.hap.system.dto.BaseDTO;

import javax.persistence.Transient;
import javax.persistence.criteria.JoinType;
import javax.validation.constraints.NotNull;

/**
 * <p>
 * 预算日记账类型关联场景
 * </p>
 * 
 * @author mouse 2019/01/07 16:39
 */
@ExtensionAttribute(disable = true)
@Table(name = "bgt_journal_type_ref_scnr")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class BgtJournalTypeRefScnr extends BaseDTO {

    public static final String FIELD_REF_ID = "refId";
    public static final String FIELD_BGT_JOURNAL_TYPE_ID = "bgtJournalTypeId";
    public static final String FIELD_SCENARIO_ID = "scenarioId";
    public static final String FIELD_SCENARIO_CODE = "scenarioCode";
    public static final String FIELD_SCENARIO_NAME = "scenarioName";


    @Id
    @GeneratedValue
    private Long refId;

    /**
     * 预算日记账类型ID
     */
    @Where
    @NotNull
    private Long bgtJournalTypeId;

    /**
     * 预算场景ID
     */
    @NotNull
    @JoinTable(name = "scenarioJoin", joinMultiLanguageTable = true, target = BgtScenario.class, type = JoinType.LEFT,
                    on = {@JoinOn(joinField = BgtScenario.FIELD_SCENARIO_ID),
                            @JoinOn(joinField = BaseDTO.FIELD_LANG, joinExpression = BaseConstants.PLACEHOLDER_LOCALE)})
    @JoinTable(name = "scenarioJoin2", joinMultiLanguageTable = false, target = BgtScenario.class, type = JoinType.LEFT,
                    on = {@JoinOn(joinField = BgtScenario.FIELD_SCENARIO_ID)})
    private Long scenarioId;

    @Transient
    @JoinColumn(joinName = "scenarioJoin2", field = BgtScenario.FIELD_SCENARIO_CODE)
    private String scenarioCode;

    @Transient
    @JoinColumn(joinName = "scenarioJoin", field = BgtScenario.FIELD_DESCRIPTION)
    private String scenarioName;


}
