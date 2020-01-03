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

import org.hibernate.validator.constraints.NotEmpty;

/**
 * <p>
 * 预算日记账类型关联预算表
 * </p>
 * 
 * @author mouse 2019/01/07 16:39
 */
@ExtensionAttribute(disable = true)
@Table(name = "bgt_journal_type_ref_strc")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class BgtJournalTypeRefStrc extends BaseDTO {

    public static final String FIELD_REF_ID = "refId";
    public static final String FIELD_BGT_JOURNAL_TYPE_ID = "bgtJournalTypeId";
    public static final String FIELD_STRUCTURE_ID = "structureId";
    public static final String FIELD_DEFAULT_FLAG = "defaultFlag";
    public static final String FIELD_STRUCTURE_CODE = "structureCode";
    public static final String FIELD_STRUCTURE_NAME = "structureName";


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
     * 预算表ID
     */
    @NotNull
    @JoinTable(name = "structureJoin", joinMultiLanguageTable = true, target = BgtStructure.class, type = JoinType.LEFT,
                    on = {@JoinOn(joinField = BgtStructure.FIELD_STRUCTURE_ID),
                            @JoinOn(joinField = BaseDTO.FIELD_LANG, joinExpression = BaseConstants.PLACEHOLDER_LOCALE)})
    @JoinTable(name = "structureJoin2", joinMultiLanguageTable = false, target = BgtStructure.class,
                    type = JoinType.LEFT, on = {@JoinOn(joinField = BgtStructure.FIELD_STRUCTURE_ID)})
    private Long structureId;

    @Transient
    @JoinColumn(joinName = "structureJoin2", field = BgtStructure.FIELD_STRUCTURE_CODE)
    private String structureCode;

    @Transient
    @JoinColumn(joinName = "structureJoin", field = BgtStructure.FIELD_DESCRIPTION)
    private String structureName;

    /**
     * 默认标志
     */
    @NotEmpty
    @Length(max = 1)
    private String defaultFlag;


}
