package com.hand.hec.bgt.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.mybatis.common.query.Where;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Table;

import com.hand.hap.system.dto.BaseDTO;

import javax.validation.constraints.NotNull;

/**
 * <p>
 * 预算日记账费用对象关联
 * </p>
 * 
 * @author mouse 2019/01/07 16:34
 */
@ExtensionAttribute(disable = true)
@Table(name = "bgt_journal_object")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class BgtJournalObject extends BaseDTO {

    public static final String FIELD_JOURNAL_OBJECT_ID = "journalObjectId";
    public static final String FIELD_JOURNAL_HEADER_ID = "journalHeaderId";
    public static final String FIELD_JOURNAL_LINE_ID = "journalLineId";
    public static final String FIELD_EXPENSE_OBJECT_TYPE_ID = "expenseObjectTypeId";
    public static final String FIELD_EXPENSE_OBJECT_ID = "expenseObjectId";
    public static final String FIELD_EXPENSE_OBJECT_DESC = "expenseObjectDesc";



    @GeneratedValue
    @Id
    @Where
    private Long journalObjectId;

    /**
     * 费用申请单头ID
     */
    @NotNull
    @Where
    private Long journalHeaderId;

    /**
     * 费用申请单行ID
     */
    @Where
    private Long journalLineId;

    /**
     * 费用对象类型ID
     */
    @NotNull
    @Where
    private Long expenseObjectTypeId;

    /**
     * 费用对象ID
     */
    private Long expenseObjectId;

    /**
     * 费用对象描述
     */
    @Length(max = 2000)
    private String expenseObjectDesc;


}
