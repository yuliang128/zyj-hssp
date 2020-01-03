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
 * 预算日记账关联预算项目
 * </p>
 * 
 * @author mouse 2019/01/07 16:39
 */
@ExtensionAttribute(disable = true)
@Table(name = "bgt_journal_type_ref_it")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class BgtJournalTypeRefIt extends BaseDTO {

    public static final String FIELD_REF_ID = "refId";
    public static final String FIELD_BGT_JOURNAL_TYPE_ID = "bgtJournalTypeId";
    public static final String FIELD_BUDGET_ITEM_ID = "budgetItemId";
    public static final String FIELD_BUDGET_ITEM_CODE = "budgetItemCode";
    public static final String FIELD_BUDGET_ITEM_NAME = "budgetItemName";


    /**
     * 主键，供其他表外键使用
     */
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
     * 预算项目ID
     */
    @NotNull
    @JoinTable(name = "budgetItemJoin", joinMultiLanguageTable = true, target = BgtBudgetItem.class, type = JoinType.LEFT,
            on = {@JoinOn(joinField = BgtBudgetItem.FIELD_BUDGET_ITEM_ID),
                    @JoinOn(joinField = BaseDTO.FIELD_LANG, joinExpression = BaseConstants.PLACEHOLDER_LOCALE)})
    @JoinTable(name = "budgetItemJoin2", joinMultiLanguageTable = false, target = BgtBudgetItem.class,
            type = JoinType.LEFT, on = {@JoinOn(joinField = BgtBudgetItem.FIELD_BUDGET_ITEM_ID)})
    private Long budgetItemId;

    @Transient
    @JoinColumn(joinName = "budgetItemJoin2", field = BgtBudgetItem.FIELD_BUDGET_ITEM_CODE)
    private String budgetItemCode;

    @Transient
    @JoinColumn(joinName = "budgetItemJoin", field = BgtBudgetItem.FIELD_DESCRIPTION)
    private String budgetItemName;


}
